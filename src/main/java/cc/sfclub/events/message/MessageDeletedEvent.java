package cc.sfclub.events.message;

public class MessageDeletedEvent extends MessageEvent {
    public MessageDeletedEvent(String userID, String transform, String message) {
        super(userID, transform, message);
    }
}
