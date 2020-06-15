package cc.sfclub.events.message;

public class MessageReceivedEvent extends MessageEvent {

    public MessageReceivedEvent(String userID, String transform, String message) {
        super(userID, transform, message);
    }
}
