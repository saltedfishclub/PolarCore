package cc.sfclub.transform.internal;

import cc.sfclub.transform.Bot;
import cc.sfclub.transform.ChatGroup;
import cc.sfclub.transform.Contact;

import java.util.Collections;
import java.util.Optional;

public class ConsoleBot extends Bot {
    private final VirtContact virtualContact = new VirtContact(0L);
    private final VirtGroup virtualGroup = new VirtGroup(0L, Collections.singleton(virtualContact));

    public ConsoleBot() {
        this.addGroup(virtualGroup, true);
    }

    @Override
    public Optional<ChatGroup> getGroup(long id) {
        return super.getGroup(id);
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public Optional<Contact> asContact(String UserID) {
        return Optional.empty();
    }
}
