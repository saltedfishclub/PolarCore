package cc.sfclub.polar.api.event.message;

import cc.sfclub.polar.api.platfrom.IContact;

public abstract class PrivateMessageEvent extends MessageEvent{
    public abstract IContact getContact();
}
