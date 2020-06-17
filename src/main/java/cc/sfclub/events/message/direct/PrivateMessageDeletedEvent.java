package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;

/**
 * When a private message was deleted
 */

public class PrivateMessageDeletedEvent extends MessageReceivedEvent {
    public PrivateMessageDeletedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
