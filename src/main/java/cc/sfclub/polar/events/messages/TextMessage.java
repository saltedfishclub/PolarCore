package cc.sfclub.polar.events.messages;

import cc.sfclub.polar.events.Message;
import lombok.Getter;

public class TextMessage extends Message {
    @Getter
    private String message;

    public TextMessage(String provider, long msgID, long user, String message, long group) {
        super(provider, msgID, user, group);
        this.message = message;
    }

    public void reply(String msg) {
        getBot().sendMessage(this, msg);
    }

    public void reply(String[] msg) {
        getBot().sendMessage(this, msg);
    }
}
