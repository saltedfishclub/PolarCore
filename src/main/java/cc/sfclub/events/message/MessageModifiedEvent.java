package cc.sfclub.events.message;

import cc.sfclub.util.Since;

/**
 * When message was modified
 */
@Since("4.0")
public class MessageModifiedEvent extends MessageEvent {
    public MessageModifiedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
