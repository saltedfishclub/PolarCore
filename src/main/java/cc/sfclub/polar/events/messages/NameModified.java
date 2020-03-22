package cc.sfclub.polar.events.messages;

import cc.sfclub.polar.events.Message;
import lombok.Getter;

/**
 * Member name changed Message(May will be deleted.)
 */
public class NameModified extends Message {
    @Getter
    private String before;
    @Getter
    private String after;

    public NameModified(String provider, int msgID, long user, long group, String before, String after) {
        super(provider, msgID, user, group);
        this.before = before;
        this.after = after;
    }
}
