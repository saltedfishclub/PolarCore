package cc.sfclub.transform;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public abstract class ChatGroup implements IReceiver {
    @Getter
    private final long ID;
    /**
     * all members from the group.
     * @return member set
     */
    @Getter
    private final Set<Contact> members;

    /**
     * the group name
     *
     * @return name
     */
    public abstract String getName();

    /**
     * get the honor of contact
     *
     * @param contact target
     * @return honor
     */
    public abstract String honorOf(Contact contact);

    /**
     * get the nick of contact
     *
     * @param contact target
     * @return nickname
     */
    public abstract String nickOf(Contact contact);

    /**
     * Simple wrapped.\n
     * Attention! If id not exists it will cause a IllegalArgumentException.
     *
     * @param id contact uid
     * @return honor
     */
    public String honorOf(long id) throws IllegalArgumentException {
        return honorOf(searchContact(id).orElseThrow(() -> {
            throw new IllegalArgumentException("ID not exists");
        }));
    }

    /**
     * Simple wrapped\n
     * Attention! If id not exists it will cause a IllegalArgumentException.
     *
     * @param id contact uid
     * @return nickname
     */
    public String nickOf(long id) throws IllegalArgumentException {
        return nickOf(searchContact(id).orElseThrow(() -> {
            throw new IllegalArgumentException("ID not exists");
        }));
    }

    /**
     * Search the contacts.
     *
     * @param id target id
     * @return target
     */
    public Optional<Contact> searchContact(long id) {
        for (Contact contact : members) {
            if (contact.getID() == id) return Optional.of(contact);
        }
        return Optional.empty();
    }

    /**
     * get contact's role in the group.
     *
     * @param contact target
     * @return target's role
     */
    public abstract Role roleOf(Contact contact);

    /**
     * get contacts which in selected role.
     *
     * @param role role
     * @return characters
     */
    public Set<Contact> contactsFromRole(Role role) {
        Set<Contact> characters = new HashSet<>();
        members.forEach(m -> {
            if (roleOf(m) == role) characters.add(m);
        });
        return characters;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) return true;
        if (!(obj instanceof Contact)) return false;
        ChatGroup group = (ChatGroup) obj;
        return group.getID() == this.ID;
    }

    /**
     * roles
     */
    public enum Role {
        OWNER, ADMIN, MEMBER;
    }
}
