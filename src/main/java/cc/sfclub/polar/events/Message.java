package cc.sfclub.polar.events;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.Event;
import cc.sfclub.polar.user.User;
import lombok.Getter;

public class Message extends Event {
    @Getter
    private String Provider;
    @Getter
    private long UID;
    @Getter
    private int MsgID;
    @Getter
    private long GroupID;

    public Message(String Provider, int MsgID, long User) {
        this.Provider = Provider;
        this.UID = User;
        this.MsgID = MsgID;
    }

    public User getUser() {
        return Core.getUserManager().getUser(UID, Provider);
    }
}
