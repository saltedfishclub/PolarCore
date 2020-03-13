package cc.sfclub.polar.wrapper;

import cc.sfclub.polar.events.messages.TextMessage;

public interface Bot {
    String getPlatfrom();

    long sendMessage(long Gid, String message);

    void deleteMsg(long msg);

    long sendMessage(TextMessage from, String output);

    long sendMessage(TextMessage from, String[] output);

    Byte[] getImage(String img);

}
