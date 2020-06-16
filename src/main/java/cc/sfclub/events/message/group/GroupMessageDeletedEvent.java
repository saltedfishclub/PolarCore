package cc.sfclub.events.message.group;

import cc.sfclub.events.message.MessageDeletedEvent;
import cc.sfclub.util.Since;
import lombok.Getter;

/**
 * When a group message was received
 */
@Since("4.0")
public class GroupMessageDeletedEvent extends MessageDeletedEvent {
    @Getter
    private final long group;

    public GroupMessageDeletedEvent(String userID, String message, long group, String transform, long messageID) {
        super(userID, message, transform, messageID);
        this.group = group;
    }
}
