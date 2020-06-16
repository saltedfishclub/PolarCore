package cc.sfclub.events.message.group;

import cc.sfclub.events.message.MessageReceivedEvent;
import cc.sfclub.util.Since;
import lombok.Getter;

/**
 * When a group message was received
 */
@Since("4.0")
public class GroupMessageReceivedEvent extends MessageReceivedEvent {
    @Getter
    private final long group;

    public GroupMessageReceivedEvent(String userID, String message, long group) {
        super(userID, message);
        this.group = group;
    }
}
