package cc.sfclub.events.message;

/**
 * When message was modified
 */

public class MessageModifiedEvent extends MessageEvent {
    public MessageModifiedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
    }
}
