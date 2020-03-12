package cc.sfclub.polar.wrapper;

import cc.sfclub.polar.events.messages.TextMessage;

public interface Bot {
    String getPlatfrom();

    void sendMessage(long Gid, String message, String platfrom);

    void deleteMsg(long msg, String platfrom);

    void sendMessage(TextMessage from, String output);

    void sendMessage(TextMessage from, String[] output);

    Byte[] getImage(String img);

}
