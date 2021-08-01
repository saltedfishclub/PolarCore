package cc.sfclub.polar.event;

import cc.sfclub.polar.platfrom.IBot;
import cc.sfclub.polar.platfrom.IPlatform;
import lombok.Getter;

/**
 * 一个平台事件。
 */
public abstract class BotEvent extends Event {
    public abstract IBot getBot();
}
