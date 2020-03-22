package cc.sfclub.polar.events.messages;

import cc.sfclub.polar.events.Message;
import lombok.Getter;

/**
 * text message.can contains some catCode
 */
public class TextMessage extends Message {
    /**
     * getMessage
     */
    @Getter
    private String message;

    public TextMessage(String provider, long msgID, long user, String message, long group) {
        super(provider, msgID, user, group);
        this.message = message;
    }

    /**
     * Reply message.
     *
     * @param msg message
     */
    public void reply(String msg) {
        getBot().sendMessage(this, msg);
    }

    /**
     * Reply message(s).
     *
     * @param msg messages.
     */
    public void reply(String[] msg) {
        getBot().sendMessage(this, msg);
    }
}
