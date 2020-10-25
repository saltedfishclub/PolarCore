package cc.sfclub.transform.internal;

import cc.sfclub.transform.ChatGroup;
import cc.sfclub.transform.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class VirtGroup extends ChatGroup {
    private static final Logger logger = LoggerFactory.getLogger("VirtualGroup");
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
        logger.info("[CONSOLE_GROUP] {}", message);
    }

    @Override
    public void reply(long messageId, String message) {
        logger.info("[CONSOLE_GROUP_REPLY] {} :: {}", messageId, message);
    }
}
