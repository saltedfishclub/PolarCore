package cc.sfclub.polar.events;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.utils.UserUtil;
import cc.sfclub.polar.wrapper.Bot;
import lombok.Getter;

public class Message {
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
        return UserUtil.getUser(UID, Provider);
    }

    public Bot getBot() {
        return Core.getInstance().getBot(this);
    }
}
