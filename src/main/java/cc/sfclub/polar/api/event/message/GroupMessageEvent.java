package cc.sfclub.polar.api.event.message;

import cc.sfclub.polar.api.platfrom.AbstractChatGroup;

public abstract class GroupMessageEvent extends MessageEvent {
    public abstract AbstractChatGroup getGroup();
}
