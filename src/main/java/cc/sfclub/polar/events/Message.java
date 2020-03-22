package cc.sfclub.polar.events;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.utils.UserUtil;
import cc.sfclub.polar.wrapper.Bot;
import lombok.Getter;

/**
 * Base Message
 */
public class Message {
    /***get message provider
     * @return provider
     */
    @Getter
    private String provider;
    /***get user id(in message provider
     * @return uid
     */
    @Getter
    private long UID;
    /***get message id(in message provider
     * @return MessageID
     */
    @Getter
    private long msgID;
    /***get group id
     * @return group id
     */
    @Getter
    private long groupID;

    public Message(String provider, long msgID, long user, long groupID) {
        this.provider = provider;
        this.UID = user;
        this.msgID = msgID;
        this.groupID = groupID;
    }

    /**
     * get User~from UserUtil
     *
     * @return User
     */
    public User getUser() {
        return UserUtil.getUser(UID, provider);
    }

    /**
     * @return provider
     */
    public Bot getBot() {
        return Core.getInstance().getBot(this);
    }
}
