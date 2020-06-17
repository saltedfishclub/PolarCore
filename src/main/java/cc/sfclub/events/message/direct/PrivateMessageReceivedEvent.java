package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;

/**
 * When a private message was received
 */

public class PrivateMessageReceivedEvent extends MessageReceivedEvent {
    public PrivateMessageReceivedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
