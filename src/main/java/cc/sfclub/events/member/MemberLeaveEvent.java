package cc.sfclub.events.member;

import cc.sfclub.events.MemberEvent;
import lombok.Builder;
import lombok.Getter;

/**
 * 当成员离开群聊后触发
 */
@Getter
@Builder
public class MemberLeaveEvent extends MemberEvent {
    public final String userID;
    public final long groupId;
    public final String transform;
}
