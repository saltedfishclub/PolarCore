package cc.sfclub.transform.internal;

import cc.sfclub.core.Core;
import cc.sfclub.transform.ChatGroup;
import cc.sfclub.transform.Contact;

import java.util.Set;

public class VirtGroup extends ChatGroup {
    public VirtGroup(long ID, Set<Contact> members) {
        super(ID, members);
    }

    @Override
    public String getName() {
        return "Console Virtual";
    }

    @Override
    public String honorOf(Contact contact) {
        return "";
    }

    @Override
    public String nickOf(Contact contact) {
        return "";
    }

    @Override
    public Role roleOf(Contact contact) {
        return Role.ADMIN;
    }

    @Override
    public void sendMessage(String message) {
        Core.getLogger().info("[CONSOLE_GROUP] {}", message);
    }

    @Override
    public void reply(long messageId, String message) {
        Core.getLogger().info("[CONSOLE_GROUP_REPLY] {} :: {}", messageId, message);
    }
}
