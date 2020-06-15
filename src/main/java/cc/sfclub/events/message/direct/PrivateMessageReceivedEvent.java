package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;

public class PrivateMessageReceivedEvent extends MessageReceivedEvent {
    public PrivateMessageReceivedEvent(String userID, String transform, String message) {
        super(userID, transform, message);
    }
}
