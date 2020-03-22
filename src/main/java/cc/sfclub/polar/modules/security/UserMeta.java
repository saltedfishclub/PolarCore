package cc.sfclub.polar.modules.security;

import java.util.Collections;
import java.util.concurrent.LinkedBlockingDeque;

/***User metadata for PolarSec.
 *
 */
public class UserMeta {
    public long lastSpeakTime = System.currentTimeMillis();
    public LinkedBlockingDeque<Integer> nearbyChats = new LinkedBlockingDeque<>();

    /**
     * add message(hashcode) to queue.
     *
     * @param i hashCode
     */
    public void addMessage(int i) {
        if (nearbyChats.size() > PolarSec.getConf().getMax_cache()) {
            nearbyChats.pollLast();
        }
        nearbyChats.add(i);
    }

    /**
     * get message delay(last~now).
     *
     * @return delay
     */
    public long getDelay() {
        return System.currentTimeMillis() - lastSpeakTime;
    }

    /**
     * stat repeated messages.
     *
     * @param i a
     * @return repeatCount
     */
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
