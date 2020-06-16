package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;
import cc.sfclub.util.Since;

/**
 * When a private message was received
 */
@Since("4.0")
public class PrivateMessageReceivedEvent extends MessageReceivedEvent {
    public PrivateMessageReceivedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
