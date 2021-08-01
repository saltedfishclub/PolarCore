package cc.sfclub.polar.event;

import cc.sfclub.polar.platfrom.IPlatform;
import lombok.Getter;

/**
 * 一个平台事件。
 */
public abstract class PlatformEvent extends Event{
    public abstract IPlatform getPlatform();
}
