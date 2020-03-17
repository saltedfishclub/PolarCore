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
    Timer timer = new Timer();

    public MessageListener(DataStorage ds) {
        PolarSec.priority = ds;
        timer.schedule(new MaybeWatchdog(), 0, 500);
    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAsyncMsg(TextMessage m) {
        if (m.getProvider().equals("CLI")) return; //ignore console
        if (m.getUser() == null) return;
        if (!PolarSec.priority.getPriority().containsKey(m.getUser().getUniqueID()))
            PolarSec.priority.getPriority().put(m.getUser().getUniqueID(), PolarSec.priority.getInitialPriority());
        String uuid = m.getUser().getUniqueID();
        if (!chatter.containsKey(uuid)) {
            chatter.put(uuid, new UserMeta());
            return;
        }
        UserMeta um = chatter.get(uuid);
        um.addMessage(m.getMessage().hashCode());
        um.addMessage(m.getMessage().hashCode());
        int secLvl = 0;
        if (Core.getConf().debug) {
            Core.getLogger().info("[PolarSec] {} = {},{}", uuid, chatter.get(uuid).getDelay(), getDelay());
        }
        if (chatter.get(uuid).getDelay() <= getDelay()) {
            if (Core.getConf().debug) {
                Core.getLogger().info("[PolarSec] UUID {} detected! (timestamp)", uuid);
            }
            secLvl++;
        }
        um.lastSpeakTime = System.currentTimeMillis();
        int sim = um.checkSimliar(m.getMessage().hashCode());
        if (sim > 0) {
            secLvl = secLvl + um.checkSimliar(m.getMessage().hashCode());
            if (Core.getConf().debug)
                Core.getLogger().info("[PolarSec] UUID {} detected! (simliar: {})", uuid, um.checkSimliar(m.getMessage().hashCode()));
        }
        if (secLvl >= maxSecLevel) {
            PolarSec.priority.getPriority().put(uuid, PolarSec.priority.getPriority().get(uuid) - 1);
            if (Core.getConf().debug)
                Core.getLogger().info("[PolarSec] Priority Changed: {} ~ {}", m.getUser().getUniqueID(), PolarSec.priority.getPriority().get(uuid));
        }
    }

    public int getDelay() {
        return (busyLevel + (3 - PolarSec.getConf().getSecurityLevel())) * 128 + PolarSec.getConf().getAdditionDelay();
    }

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