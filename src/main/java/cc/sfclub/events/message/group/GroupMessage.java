package cc.sfclub.events.message.group;

import cc.sfclub.core.Core;
import cc.sfclub.events.message.Message;
import cc.sfclub.transform.ChatGroup;
import lombok.Getter;

/**
 * When a group message was received
 */

public class GroupMessage extends Message {
    @Getter
    private final long groupId;
    @Getter
    private final ChatGroup group;

    public GroupMessage(String userID, String message, long group, String transform, long messageID) {
        super(userID, message, transform, messageID);
        this.groupId = group;
        this.group = Core.get()
                .bot(getTransform())
                .orElseThrow(() -> new NullPointerException("Bot with transform " + getTransform() + "not found!"))
                .getGroup(groupId).orElseThrow(() -> new NullPointerException("Unknown error happened.(Group not found)"));
    }

    @Override
    public void reply(long msgId, String message) {
        group.reply(msgId, message);
    }

    public void reply(String message) {
        group.reply(super.getMessageID(), message);
    }
}
