package cc.sfclub.polar.api.event.message.contact;

import cc.sfclub.polar.api.event.message.PrivateMessageEvent;
import cc.sfclub.polar.api.platfrom.IContact;
import cc.sfclub.polar.api.platfrom.IMessageSource;
import cc.sfclub.polar.api.platfrom.IPlatformBot;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class PrivateMessage extends PrivateMessageEvent {
    private IPlatformBot bot;
    private IContact contact;
    private Instant time;
    private String message;
    private long id;

    @Override
    public IMessageSource getMessageSource() {
        return contact;
    }
}
