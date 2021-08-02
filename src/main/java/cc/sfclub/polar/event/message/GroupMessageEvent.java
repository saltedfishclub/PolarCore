package cc.sfclub.polar.event.message;

import cc.sfclub.polar.platfrom.AbstractChatGroup;

public abstract class GroupMessageEvent extends MessageEvent {
    public abstract AbstractChatGroup getGroup();
}
