package cc.sfclub.polar.wrapper;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;

public final class SimpleWrapper implements Bot {

    @Override
    public String getPlatfrom() {
        return "CLI";
    }

    @Override
    public void sendMessage(long id, String message, String platfrom) {
        Core.getLogger().info("ID: {} Msg: {} Platfrom: {}", id, message, platfrom);
    }

    @Override
    public void deleteMsg(long msg, String platfrom) {

    }

    @Override
    public void sendMessage(TextMessage from, String output) {
        sendMessage(from.getGroupID(), output, from.getProvider());
    }

    @Override
    public void sendMessage(TextMessage from, String[] output) {
        for (String s : output) {
            sendMessage(from, s);
        }
    }

    @Override
    public Byte[] getImage(String img) {
        return new Byte[0];
    }
}
