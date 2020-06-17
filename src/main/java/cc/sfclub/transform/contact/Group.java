package cc.sfclub.transform.contact;

import cc.sfclub.transform.Status;
import cc.sfclub.transform.Talkable;

import java.util.List;


public interface Group extends Talkable {

    long getGroupID();


    List<Contact> getMembers();


    Profile getProfile(Contact contact);


    Status.Message reply(long MessageID, String text);
}
