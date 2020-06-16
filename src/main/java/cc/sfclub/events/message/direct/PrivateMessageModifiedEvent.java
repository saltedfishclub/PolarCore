package cc.sfclub.events.message.direct;

import cc.sfclub.events.message.MessageReceivedEvent;
import cc.sfclub.util.Since;

/**
 * When a private message was edited.
 */
@Since("4.0")
public class PrivateMessageModifiedEvent extends MessageReceivedEvent {
    public PrivateMessageModifiedEvent(String userID, String message) {
        super(userID, message);
    }
}
