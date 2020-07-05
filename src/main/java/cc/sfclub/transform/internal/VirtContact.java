package cc.sfclub.transform.internal;

import cc.sfclub.core.Core;
import cc.sfclub.transform.Contact;
import cc.sfclub.user.User;

public class VirtContact extends Contact {
    public VirtContact(long ID) {
        super(ID);
    }

    @Override
    public String getNickname() {
        return "NICK_NAME";
    }

    @Override
    public String getUsername() {
        return "CONSOLE";
    }

    @Override
    public User asPermObj() {
        return Core.get().console();
    }

    @Override
    public void sendMessage(String message) {
        Core.getLogger().info("[CONSOLE_PRIVATE_MESSAGE] {}", message);
    }

    @Override
    public void reply(long messageId, String message) {
        Core.getLogger().info("[CONSOLE_PRIVATE_REPLY] {}", message);
    }
}
