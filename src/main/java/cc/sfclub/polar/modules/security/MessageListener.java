package cc.sfclub.polar.modules.security;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MessageListener {
    private HashMap<String, UserMeta> chatter = new HashMap<>();
    private PolarSec psec;
    private int messageCount;
    private Timer timer;
    private int maxSecLevel = PolarSec.getConf().getSecurityLevel();

    public MessageListener(PolarSec psec) {
        this.psec = psec;
        timer = new Timer();
        timer.schedule(new MaybeWatchdog(), 0, 1000);
    }

    public void destory() {
        timer.cancel();
    }

    /*
        Message Filter.
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 10)
    public void onMessage(TextMessage m) {
        if (m.getProvider().equals("CLI")) return; //ignore console
        if (m.getUser() == null) return;
        if (!PolarSec.getConf().getPriority().containsKey(m.getUser().getUniqueID()))
            PolarSec.getConf().getPriority().put(m.getUser().getUniqueID(), PolarSec.getConf().getInitialPriority());
        if (PolarSec.getConf().getPriority().get(m.getUser().getUniqueID()) < psec.busyLevel) {
            UserMeta um = chatter.get(m.getUser().getUniqueID());
            if (Core.getConf().debug)
                Core.getLogger().info("[PolarSec] Capture!! User: {} ,Priority: {}/{} ,Delay: {}", m.getUser().getUniqueID(), PolarSec.getConf().getPriority().get(m.getUser().getUniqueID()), psec.busyLevel, um.getDelay());
            Core.getInstance().getMessage().cancelEventDelivery(m);
            return;
        }
        messageCount++;
    }

    /*
        Action Analyzer.use async may cause some error
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAsyncMsg(TextMessage m) {
        if (m.getProvider().equals("CLI")) return; //ignore console
        if (m.getUser() == null) return;
        String UUID = m.getUser().getUniqueID();
        if (!PolarSec.getConf().getPriority().containsKey(UUID))
            PolarSec.getConf().getPriority().put(UUID, PolarSec.getConf().getInitialPriority());
        if (!chatter.containsKey(UUID)) {
            chatter.put(UUID, new UserMeta());
            return;
        }
        UserMeta um = chatter.get(UUID);
        um.addMessage(m.getMessage().hashCode());
        um.addMessage(m.getMessage().hashCode());
        int secLvl = 0;
        boolean debug = Core.getConf().debug;
        if (Core.getConf().debug) {
            Core.getLogger().info("[PolarSec] {} = {},{}", UUID, chatter.get(UUID).getDelay(), getDelay());
        }
        if (chatter.get(UUID).getDelay() <= getDelay()) {
            if (debug) {
                Core.getLogger().info("[PolarSec] UUID {} detected! (timestamp)", UUID);
            }
            secLvl++;
        }
        um.lastSpeakTime = System.currentTimeMillis();
        int sim = um.checkSimliar(m.getMessage().hashCode());
        if (sim > 0) {
            secLvl = secLvl + um.checkSimliar(m.getMessage().hashCode());
            if (debug)
                Core.getLogger().info("[PolarSec] UUID {} detected! (simliar: {})", UUID, um.checkSimliar(m.getMessage().hashCode()));
        }
        if (secLvl >= maxSecLevel) {
            PolarSec.getConf().getPriority().put(UUID, PolarSec.getConf().getPriority().get(UUID) - 1);
            if (debug)
                Core.getLogger().info("[PolarSec] Priority Changed: {} ~ {}", UUID, PolarSec.getConf().getPriority().get(UUID));
        }
    }

    public int getDelay() {
        return (psec.busyLevel + (3 - PolarSec.getConf().getSecurityLevel())) * 128 + PolarSec.getConf().getAdditionDelay();
    }

    /*
    It looks like a watchdog.resets user that.getConf()=-1.
    also control busyLevel
     */
    class MaybeWatchdog extends TimerTask {
        private int c;
        private int Counter;
        private int cC;

        public void run() {
            Counter++;
            cC++;
            if (cC > 60) {
                PolarSec.getConf().getPriority().keySet().forEach(a -> {
                    if (PolarSec.getConf().getPriority().get(a) == -1) {
                        PolarSec.getConf().getPriority().put(a, 2);
                    }
                });
                cC = 0;
            }
            if (Counter > 6) {
                Counter = 0;
                if (messageCount > PolarSec.getConf().getSecurityLevel() * 25) {
                    messageCount = 0;
                    c = 0;
                    if (psec.busyLevel != 5) {
                        psec.busyLevel++;
                        if (Core.getConf().debug) Core.getLogger().info("[PolarSec] busyLevel Up: {}", psec.busyLevel);
                    } else {
                        Core.getLogger().warn("[PolarSec] busyLevel MAX!!");
                    }
                } else {
                    messageCount = 0;
                    c++;
                    if (c > 4 && psec.busyLevel != 0) {
                        psec.busyLevel--;
                    }
                }
            }
        }
    }
}