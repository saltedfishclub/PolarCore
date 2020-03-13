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
    private long MsgID;
    @Getter
    private long GroupID;

    public Message(String Provider, long MsgID, long User, long GroupID) {
        this.Provider = Provider;
        this.UID = User;
        this.MsgID = MsgID;
        this.GroupID = GroupID;
    }

    public User getUser() {
        return Core.getUserManager().getUser(UID, Provider);
    }
}
