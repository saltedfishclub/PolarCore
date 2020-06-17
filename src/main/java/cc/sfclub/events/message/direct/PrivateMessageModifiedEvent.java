package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;

/**
 * When a private message was edited.
 */

public class PrivateMessageModifiedEvent extends MessageReceivedEvent {
    public PrivateMessageModifiedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
