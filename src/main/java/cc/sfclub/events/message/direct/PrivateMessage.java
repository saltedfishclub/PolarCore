package cc.sfclub.events.message.direct;

import cc.sfclub.core.Core;
import cc.sfclub.events.internal.MessageListener;
import cc.sfclub.events.message.Message;
import cc.sfclub.transform.Bot;
import cc.sfclub.transform.Contact;
import cc.sfclub.user.User;
import lombok.Getter;

import java.util.function.Consumer;

/**
 * When a private message was received
 */

public class PrivateMessage extends Message {
    @Getter
    private final Contact contact;

    public PrivateMessage(String userID, String message, String transform, long messageID) {
        super(userID, message, transform, messageID);
        this.contact = Core.get()
                .bot(getTransform())
                .orElseThrow(() -> new NullPointerException("Bot with transform " + getTransform() + "not found!"))
                .asContact(userID).orElseThrow(() -> new NullPointerException("Unknown error happened.(Contact not found)"));
    }

    @Override
    public void reply(long msgId, String message) {
        contact.reply(msgId, message);
    }

    public void reply(String message) {
        reply(super.getMessageID(), message);
    }

    public static void subscribeAlways(Consumer<PrivateMessage> handler) {
        MessageListener.mHandlers.add(handler);
    }

    public Contact senderAsContact() {
        return getTransformAsBot().getContact(Long.parseLong(getSender().getPlatformId())).orElseThrow(NullPointerException::new);
    }

    public Bot getTransformAsBot() {
        return Core.get().bot(getTransform()).orElseThrow(NullPointerException::new);
    }

    public User getSender() {
        return Core.get().userManager().byUUID(getUserID());
    }
}
