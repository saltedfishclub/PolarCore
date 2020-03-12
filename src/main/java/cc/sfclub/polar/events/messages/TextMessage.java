package cc.sfclub.polar.events.messages;

import cc.sfclub.polar.events.Message;
import lombok.Getter;

public class TextMessage extends Message {
    @Getter
    private String Message;

    public TextMessage(String Provider, int MsgID, long User, String Message) {
        super(Provider, MsgID, User);
        this.Message = Message;
    }

}
