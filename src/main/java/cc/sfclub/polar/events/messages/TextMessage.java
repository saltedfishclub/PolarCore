package cc.sfclub.polar.events.messages;

import cc.sfclub.polar.events.Message;
import lombok.Getter;

public class TextMessage extends Message {
    @Getter
    private String Message;

    public TextMessage(String Provider, long MsgID, long User, String Message, long Group) {
        super(Provider, MsgID, User, Group);
        this.Message = Message;
    }

    public void reply(String msg) {
        getBot().sendMessage(this, msg);
    }

    public void reply(String[] msg) {
        getBot().sendMessage(this, msg);
    }
}
