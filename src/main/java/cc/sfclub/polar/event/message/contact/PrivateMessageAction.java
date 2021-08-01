package cc.sfclub.polar.event.message.contact;

import cc.sfclub.polar.event.message.MessageAction;
import cc.sfclub.polar.event.message.PrivateMessageEvent;
import cc.sfclub.polar.platfrom.IBot;
import cc.sfclub.polar.platfrom.IContact;
import cc.sfclub.polar.platfrom.IMessageSource;
import cc.sfclub.polar.platfrom.IPlatform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PrivateMessageAction extends PrivateMessageEvent {
    private final MessageAction action;
    private final IContact contact;
    private final long id;
    private final String message;
    private final IBot bot;
    private long time;

    @Override
    public IMessageSource getMessageSource() {
        return contact;
    }
}
