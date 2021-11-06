package cc.sfclub.polar.api.event.message.group;

import cc.sfclub.polar.api.event.message.GroupMessageEvent;
import cc.sfclub.polar.api.platfrom.AbstractChatGroup;
import cc.sfclub.polar.api.platfrom.IMember;
import cc.sfclub.polar.api.platfrom.IMessageSource;
import cc.sfclub.polar.api.platfrom.IPlatformBot;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class GroupMessage extends GroupMessageEvent {
    private final AbstractChatGroup group;
    private final Instant time;
    private final String message;
    private final IPlatformBot bot;
    private final IMember sender;
    private final long id;

    @Override
    public IPlatformBot getBot() {
        return bot;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public IMessageSource getMessageSource() {
        return sender;
    }

    @Override
    public Instant getTime() {
        return time;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public AbstractChatGroup getGroup() {
        return group;
    }
    public void reply(String messsage){
        sender.reply(id,message);
    }
}
