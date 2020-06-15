package cc.sfclub.events.message.group;

import cc.sfclub.events.message.MessageDeletedEvent;
import lombok.Getter;

public class GroupMessageDeletedEvent extends MessageDeletedEvent {
    @Getter
    private final long group;

    public GroupMessageDeletedEvent(String userID, String transform, String message, long group) {
        super(userID, transform, message);
        this.group = group;
    }
}
