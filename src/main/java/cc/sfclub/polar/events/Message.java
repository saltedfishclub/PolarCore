package cc.sfclub.polar.events;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.utils.UserUtil;
import cc.sfclub.polar.wrapper.Bot;
import lombok.Getter;

public class Message {
    @Getter
    private String provider;
    @Getter
    private long UID;
    @Getter
    private long msgID;
    @Getter
    private long groupID;

    public Message(String provider, long msgID, long user, long groupID) {
        this.provider = provider;
        this.UID = user;
        this.msgID = msgID;
        this.groupID = groupID;
    }

    public User getUser() {
        return UserUtil.getUser(UID, provider);
    }

    public Bot getBot() {
        return Core.getInstance().getBot(this);
    }
}
