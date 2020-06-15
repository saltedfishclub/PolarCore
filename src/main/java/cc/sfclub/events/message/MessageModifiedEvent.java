package cc.sfclub.events.message;

public class MessageModifiedEvent extends MessageEvent {
    public MessageModifiedEvent(String userID, String transform, String message) {
        super(userID, transform, message);
    }
}
