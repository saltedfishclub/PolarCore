package cc.sfclub.events.message;

import cc.sfclub.util.Since;

/**
 * When a group message was received
 */
@Since("4.0")
public class MessageDeletedEvent extends MessageEvent {
    public MessageDeletedEvent(String userID, String message) {
        super(userID, message);
    }
}
