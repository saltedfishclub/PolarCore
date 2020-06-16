package cc.sfclub.transform.contact;

import cc.sfclub.transform.Talkable;
import cc.sfclub.util.Since;

import java.util.List;

@Since("4.0")
public interface Group extends Talkable {
    @Since("4.0")
    long getGroupID();

    @Since("4.0")
    List<Contact> getMembers();

    @Since("4.0")
    Profile getProfile(Contact contact);

    @Since("4.0")
    Contact getContact(long TID);
}
