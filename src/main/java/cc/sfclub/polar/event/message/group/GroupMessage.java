package cc.sfclub.polar.event.message.group;

import cc.sfclub.polar.event.message.GroupMessageEvent;
import cc.sfclub.polar.event.message.MessageEvent;
import cc.sfclub.polar.platfrom.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GroupMessage extends GroupMessageEvent {
    private final AbstractChatGroup group;
    private final long time;
    private final String message;
    private final IBot bot;
    private final IMember sender;
    private final long id;
    @Override
    public IBot getBot() {
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
    public long getTime() {
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
