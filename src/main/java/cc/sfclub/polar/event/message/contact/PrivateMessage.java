package cc.sfclub.polar.event.message.contact;

import cc.sfclub.polar.event.message.PrivateMessageEvent;
import cc.sfclub.polar.platfrom.IContact;
import cc.sfclub.polar.platfrom.IMessageSource;
import cc.sfclub.polar.platfrom.IPlatform;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PrivateMessage extends PrivateMessageEvent {
    private IPlatform platform;
    private IContact contact;
    private long time;
    private String message;
    private long id;
    @Override
    public IMessageSource getMessageSource() {
        return contact;
    }
}
