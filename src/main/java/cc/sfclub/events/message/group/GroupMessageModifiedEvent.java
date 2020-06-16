package cc.sfclub.events.message.group;

import cc.sfclub.events.message.MessageModifiedEvent;
import lombok.Getter;

/**
 * When a group message was modified
 */
public class GroupMessageModifiedEvent extends MessageModifiedEvent {
    @Getter
    private final long group;

    public GroupMessageModifiedEvent(String userID, String message, long group) {
        super(userID, message);
        this.group = group;
    }
}
