package cc.sfclub.polar.events.messages;

/**
 * "Message Deleted" Message
 */
public class DeleteMessage extends TextMessage {
    public DeleteMessage(String provider, int msgID, long user, String message, long group) {
        super(provider, msgID, user, message, group);
    }
}
