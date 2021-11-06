package cc.sfclub.polar.api.event;

import cc.sfclub.polar.api.platfrom.IPlatformBot;

/**
 * 一个平台事件。
 */
public abstract class BotEvent extends Event {
    public abstract IPlatformBot getBot();
}
