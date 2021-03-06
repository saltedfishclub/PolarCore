package cc.sfclub.events.message.group;

import cc.sfclub.core.Core;
import cc.sfclub.events.message.MessageModifiedEvent;
import cc.sfclub.transform.ChatGroup;
import lombok.Getter;

/**
 * When a group message was modified
 */
public class GroupMessageModifiedEvent extends MessageModifiedEvent {
    @Getter
    private final long groupId;
    @Getter
    private final ChatGroup group;

    public GroupMessageModifiedEvent(String userID, String message, long group, String transform, long messageID) {
        super(userID, message, transform, messageID);
        this.groupId = group;
        this.group = Core.get()
                .bot(getTransform())
                .orElseThrow(() -> new NullPointerException("Bot with transform " + getTransform() + "not found!"))
                .getGroup(groupId).orElseThrow(() -> new NullPointerException("Unknown error happened.(Group not found)"));
    }
}
