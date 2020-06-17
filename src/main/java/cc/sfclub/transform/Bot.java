package cc.sfclub.transform;

import cc.sfclub.transform.contact.Contact;
import cc.sfclub.transform.contact.Group;
import cc.sfclub.transform.contact.Profile;


public abstract class Bot {

    public abstract Group getGroup(long GroupID);


    public abstract Transform getTransform();


    public Status.Message sendGroupMessage(long GroupID, String message) {
        return getGroup(GroupID).sendMessage(message);
    }


    public abstract Contact getContact(long TID);


    public Profile getProfile(long GroupID, long TID) {
        return getGroup(GroupID).getProfile(getContact(TID));
    }
}
