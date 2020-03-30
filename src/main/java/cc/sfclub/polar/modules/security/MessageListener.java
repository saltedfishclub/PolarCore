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
    private int busyLevel = 0;
    private int messageCount;
    private int maxSecLevel = PolarSec.getConf().getSecurityLevel();

    public MessageListener(DataStorage ds) {
        PolarSec.priority = ds;
        Timer timer = new Timer();
        timer.schedule(new MaybeWatchdog(), 0, 500 * 1000);
    }

    /*
        Message Filter.
     */
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 10)
    public void onMessage(TextMessage m) {
        if (m.getProvider().equals("CLI")) return; //ignore console
        if (m.getUser() == null) return;
        if (!PolarSec.priority.getPriority().containsKey(m.getUser().getUniqueID()))
            PolarSec.priority.getPriority().put(m.getUser().getUniqueID(), PolarSec.priority.getInitialPriority());
        if (PolarSec.priority.getPriority().get(m.getUser().getUniqueID()) < busyLevel) {
            UserMeta um = chatter.get(m.getUser().getUniqueID());
            if (Core.getConf().debug)
                Core.getLogger().info("[PolarSec] Capture!! User: {} ,Priority: {}/{} ,Delay: {}", m.getUser().getUniqueID(), PolarSec.priority.getPriority().get(m.getUser().getUniqueID()), busyLevel, um.getDelay());
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
        if (!PolarSec.priority.getPriority().containsKey(UUID))
            PolarSec.priority.getPriority().put(UUID, PolarSec.priority.getInitialPriority());
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
            PolarSec.priority.getPriority().put(UUID, PolarSec.priority.getPriority().get(UUID) - 1);
            if (debug)
                Core.getLogger().info("[PolarSec] Priority Changed: {} ~ {}", UUID, PolarSec.priority.getPriority().get(UUID));
        }
    }

    public int getDelay() {
        return (busyLevel + (3 - PolarSec.getConf().getSecurityLevel())) * 128 + PolarSec.getConf().getAdditionDelay();
    }

    /*
    It looks like a watchdog.resets user that priority=-1.
    also control busyLevel
     */
    class MaybeWatchdog extends TimerTask {
        private int c;
        private int Counter;
        private int cC;

        public void run() {
            Counter++;
            cC++;
            if (cC > 100) {
                PolarSec.priority.getPriority().keySet().forEach(a -> {
                    if (PolarSec.priority.getPriority().get(a) == -1) {
                        PolarSec.priority.getPriority().put(a, 2);
                    }
                });
                cC = 0;
            }
            if (Counter > 6) {
                Counter = 0;
                if (messageCount > PolarSec.getConf().getSecurityLevel() * 5) {
                    messageCount = 0;
                    c = 0;
                    if (busyLevel != 5) {
                        busyLevel++;
                        if (Core.getConf().debug) Core.getLogger().info("[PolarSec] busyLevel Up: {}", busyLevel);
                    } else {
                        Core.getLogger().warn("[PolarSec] busyLevel MAX!!");
                    }
                } else {
                    messageCount = 0;
                    c++;
                    if (c > 4 && busyLevel != 0) {
                        busyLevel--;
                    }
                }
            }
        }
    }
}