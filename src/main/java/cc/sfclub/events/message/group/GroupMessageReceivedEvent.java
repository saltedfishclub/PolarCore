package cc.sfclub.events.message.group;

import cc.sfclub.events.message.MessageReceivedEvent;
import lombok.Getter;

public class GroupMessageReceivedEvent extends MessageReceivedEvent {
    @Getter
    private final long group;

    public GroupMessageReceivedEvent(String userID, String transform, String message, long group) {
        super(userID, transform, message);
        this.group = group;
    }
}
