package cc.sfclub.polar.event.message.group;

import cc.sfclub.polar.event.message.GroupMessageEvent;
import cc.sfclub.polar.event.message.MessageAction;
import cc.sfclub.polar.platfrom.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.AbstractList;

@Getter
@RequiredArgsConstructor
public class GroupMessageAction extends GroupMessageEvent {
    private final IBot bot;
    private final AbstractChatGroup group;
    private final IMember sender;
    private final String message;
    private final long id;
    private final long time;
    private MessageAction action;
    @Override
    public IMessageSource getMessageSource() {
        return sender;
    }
}
