package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;

public class PrivateMessageDeletedEvent extends MessageReceivedEvent {
    public PrivateMessageDeletedEvent(String userID, String transform, String message) {
        super(userID, transform, message);
    }
}
