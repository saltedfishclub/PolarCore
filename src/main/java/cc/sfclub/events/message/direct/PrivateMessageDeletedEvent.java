package cc.sfclub.events.message.direct;

import cc.sfclub.core.Core;
import cc.sfclub.events.message.Message;
import cc.sfclub.transform.Contact;
import lombok.Getter;

/**
 * When a private message was deleted
 */

public class PrivateMessageDeletedEvent extends Message {
    @Getter
    private final Contact contact;

    public PrivateMessageDeletedEvent(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
        this.contact = Core.get()
                .bot(getTransform())
                .orElseThrow(() -> new NullPointerException("Bot with transform " + getTransform() + "not found!"))
                .asContact(userID).orElseThrow(() -> new NullPointerException("Unknown error happened.(Contact not found)"));
    }
}
