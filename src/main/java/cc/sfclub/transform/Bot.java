package cc.sfclub.transform;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Bot {
    private static final Map<Long, ChatGroup> groupCache = new HashMap<>();
    private static final Map<Long, Contact> contactCache = new HashMap<>();

    /**
     * Add a group to simple cache.
     *
     * @param group     group
     * @param overwrite
     */
    private void addGroup(ChatGroup group, boolean overwrite) {
        if (overwrite) {
            groupCache.remove(group.getID());
        }
        if (!groupCache.containsKey(group.getID())) {
            groupCache.put(group.getID(), group);
        }
    }

    /**
     * get a group reference.
     *
     * @param id group id
     * @return group
     */
    public Optional<ChatGroup> getGroup(long id) {
        return Optional.ofNullable(groupCache.get(id));
    }

    /**
     * get a contact reference
     *
     * @param id uid
     * @return contact
     */
    public Optional<Contact> getContact(long id) {
        return Optional.ofNullable(contactCache.get(id));
    }

    /**
     * Add a contact to cache
     *
     * @param contact
     * @param overwrite
     */
    public void addContact(Contact contact, boolean overwrite) {
        if (overwrite) {
            contactCache.remove(contact.getID());
        }
        if (!contactCache.containsKey(contact.getID())) {
            contactCache.put(contact.getID(), contact);
        }
    }
}
