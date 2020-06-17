package cc.sfclub.events.message;

/**
 * When message was received
 */

public class MessageDeletedEvent extends MessageEvent {
    public MessageDeletedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
