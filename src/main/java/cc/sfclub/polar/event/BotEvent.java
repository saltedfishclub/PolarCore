package cc.sfclub.polar.event;

import cc.sfclub.polar.platfrom.IPlatformBot;

/**
 * 一个平台事件。
 */
public abstract class BotEvent extends Event {
    public abstract IPlatformBot getBot();
}
