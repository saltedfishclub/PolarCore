package cc.sfclub.polar.modules.security;

import java.util.Collections;
import java.util.concurrent.LinkedBlockingDeque;

public class UserMeta {
    public long lastSpeakTime = System.currentTimeMillis();
    public LinkedBlockingDeque<Integer> nearbyChats = new LinkedBlockingDeque<>();

    public void addMessage(int i) {
        if (nearbyChats.size() > PolarSec.getConf().getMax_cache()) {
            nearbyChats.pollLast();
        }
        nearbyChats.add(i);
    }

    public long getDelay() {
        return System.currentTimeMillis() - lastSpeakTime;
    }

    public int checkSimliar(int i) {
        if (nearbyChats.size() >= PolarSec.getConf().getMax_cache()) {
            if (Collections.frequency(nearbyChats, i) > 2) {
                return 1;
            } else {
                return 0;
            }
        } else {
            {
                return 0;
            }
        }

    }
}
