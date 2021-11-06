package cc.sfclub.polar.api.event.message.contact;

import cc.sfclub.polar.api.event.message.MessageAction;
import cc.sfclub.polar.api.event.message.PrivateMessageEvent;
import cc.sfclub.polar.api.platfrom.IContact;
import cc.sfclub.polar.api.platfrom.IMessageSource;
import cc.sfclub.polar.api.platfrom.IPlatformBot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class PrivateMessageAction extends PrivateMessageEvent {
    private final MessageAction action;
    private final IContact contact;
    private final long id;
    private final String message;
    private final IPlatformBot bot;
    private Instant time;

    @Override
    public IMessageSource getMessageSource() {
        return contact;
    }
}
