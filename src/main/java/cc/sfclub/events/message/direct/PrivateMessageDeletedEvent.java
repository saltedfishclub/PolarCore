package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;
import cc.sfclub.util.Since;

/**
 * When a private message was deleted
 */
@Since("4.0")
public class PrivateMessageDeletedEvent extends MessageReceivedEvent {
    public PrivateMessageDeletedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
