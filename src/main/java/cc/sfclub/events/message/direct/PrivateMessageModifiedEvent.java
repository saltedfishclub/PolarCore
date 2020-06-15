package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;

public class PrivateMessageModifiedEvent extends MessageReceivedEvent {
    public PrivateMessageModifiedEvent(String userID, String transform, String message) {
        super(userID, transform, message);
    }
}
