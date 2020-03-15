package cc.sfclub.polar.wrapper;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;

public final class SimpleWrapper implements Bot {

    @Override
    public String getPlatfrom() {
        return "CLI";
    }

    @Override
    public long sendMessage(long id, String message) {
        Core.getLogger().info("ID: {} Msg: {}", id, message);
        return 0L;
    }

    @Override
    public void deleteMsg(long msg) {

    }

    @Override
    public long sendMessage(TextMessage from, String output) {
        sendMessage(from.getGroupID(), output);
        return 0L;
    }

    @Override
    public long sendMessage(TextMessage from, String[] output) {
        for (String s : output) {
            sendMessage(from, s);
        }
        return 0L;
    }

    @Override
    public Byte[] getImage(String img) {
        return new Byte[0];
    }
}
