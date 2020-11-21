package cc.sfclub.events.member;

import cc.sfclub.events.MemberEvent;

/**
 * 成员请求加入群聊
 */
public abstract class MemberRequestJoinEvent extends MemberEvent {
    public abstract boolean deal(boolean accept);

    public abstract String getMessage();
}
