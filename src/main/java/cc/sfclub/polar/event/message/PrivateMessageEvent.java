package cc.sfclub.polar.event.message;

import cc.sfclub.polar.platfrom.IContact;

public abstract class PrivateMessageEvent extends MessageEvent{
    public abstract IContact getContact();
}
