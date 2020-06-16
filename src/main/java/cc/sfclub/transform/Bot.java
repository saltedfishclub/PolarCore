package cc.sfclub.transform;

import cc.sfclub.transform.contact.Contact;
import cc.sfclub.transform.contact.Group;
import cc.sfclub.transform.contact.Profile;
import cc.sfclub.util.Since;

@Since("4.0")
public abstract class Bot {
    @Since("4.0")
    public abstract Group getGroup(long GroupID);

    @Since("4.0")
    public abstract Transform getTransform();

    @Since("4.0")
    public Status.Message sendGroupMessage(long GroupID, String message) {
        return getGroup(GroupID).sendMessage(message);
    }

    @Since("4.0")
    public abstract Contact getContact(long TID);

    @Since("4.0")
    public Profile getProfile(long GroupID, long TID) {
        return getGroup(GroupID).getProfile(getContact(TID));
    }
}
