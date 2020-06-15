package cc.sfclub.events.message.group;

import cc.sfclub.events.message.MessageModifiedEvent;
import lombok.Getter;

public class GroupMessageModifiedEvent extends MessageModifiedEvent {
    @Getter
    private final long group;

    public GroupMessageModifiedEvent(String userID, String transform, String message, long group) {
        super(userID, transform, message);
        this.group = group;
    }
}
