package cc.sfclub.events.message.group;

import cc.sfclub.core.modules.Core;
import cc.sfclub.events.message.MessageReceivedEvent;
import cc.sfclub.util.Since;
import lombok.Getter;

/**
 * When a group message was received
 */
@Since("4.0")
public class GroupMessageReceivedEvent extends MessageReceivedEvent {
    @Getter
    private final long group;

    public GroupMessageReceivedEvent(String userID, String message, long group, String transform, long messageID) {
        super(userID, message, transform, messageID);
        this.group = group;
    }

    public void reply(String message) {
        Core.get().bot(super.getTransform()).get().getGroup(group).reply(super.getMessageID(), message);
    }
}