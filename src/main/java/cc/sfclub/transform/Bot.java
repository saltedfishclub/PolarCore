package cc.sfclub.transform;

import cc.sfclub.transform.contact.Group;
import cc.sfclub.util.Since;

@Since("4.0")
public interface Bot {
    @Since("4.0")
    Group getGroup(long GroupID);
}
