package cc.sfclub.polar.api.event.message;

import cc.sfclub.polar.api.event.BotEvent;
import cc.sfclub.polar.api.platfrom.IMessageSource;

import java.time.Instant;

/**
 * 信息事件。每个信息总是拥有对应的 platform.
 */
public abstract class MessageEvent extends BotEvent {
    public abstract String getMessage();

    public abstract IMessageSource getMessageSource();

    public abstract Instant getTime();

    public abstract long getId();
}
