package cc.sfclub.polar.api.event.message.group;

import cc.sfclub.polar.api.event.message.GroupMessageEvent;
import cc.sfclub.polar.api.event.message.MessageAction;
import cc.sfclub.polar.api.platfrom.AbstractChatGroup;
import cc.sfclub.polar.api.platfrom.IMember;
import cc.sfclub.polar.api.platfrom.IMessageSource;
import cc.sfclub.polar.api.platfrom.IPlatformBot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class GroupMessageAction extends GroupMessageEvent {
    private final IPlatformBot bot;
    private final AbstractChatGroup group;
    private final IMember sender;
    private final String message;
    private final long id;
    private final Instant time;
    private MessageAction action;
    @Override
    public IMessageSource getMessageSource() {
        return sender;
    }
}
