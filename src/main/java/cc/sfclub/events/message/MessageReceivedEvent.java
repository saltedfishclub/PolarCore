package cc.sfclub.events.message;

import cc.sfclub.util.Since;

/**
 * When message was received
 */
@Since("4.0")
public class MessageReceivedEvent extends MessageEvent {

    public MessageReceivedEvent(String userID, String message) {
        super(userID, message);
    }
}
