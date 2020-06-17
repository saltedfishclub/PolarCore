package cc.sfclub.events.message.group;

import cc.sfclub.events.message.MessageDeletedEvent;
import lombok.Getter;

/**
 * When a group message was received
 */

public class GroupMessageDeletedEvent extends MessageDeletedEvent {
    @Getter
    private final long group;

    public GroupMessageDeletedEvent(String userID, String message, long group, String transform, long messageID) {
        super(userID, message, transform, messageID);
        this.group = group;
    }
}
