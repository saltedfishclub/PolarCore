package cc.sfclub.polar.wrapper;

import cc.sfclub.polar.events.messages.TextMessage;

public abstract class Bot {
    public abstract String getPlatfrom();

    public abstract long sendMessage(long Gid, String message);

    public void deleteMsg(long msg) {
    }

    public long sendMessage(TextMessage from, String output) {
        return sendMessage(from.getGroupID(), output);
    }

    public long sendMessage(TextMessage from, String[] in) {
        StringBuilder str = new StringBuilder();
        for (String string : in) {
            str.append(string).append("\n");
        }
        return sendMessage(from, str.toString());
    }

    public Byte[] getImage(String img) {
        return new Byte[0];
    }

}
