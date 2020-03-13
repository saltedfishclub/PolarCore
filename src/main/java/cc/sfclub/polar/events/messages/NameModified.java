package cc.sfclub.polar.events.messages;

import cc.sfclub.polar.events.Message;
import lombok.Getter;

public class NameModified extends Message {
    @Getter
    private String before;
    @Getter
    private String after;

    public NameModified(String Provider, int MsgID, long User, long Group, String before, String after) {
        super(Provider, MsgID, User, Group);
        this.before = before;
        this.after = after;
    }
}
