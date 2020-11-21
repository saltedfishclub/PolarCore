package cc.sfclub.events;

import lombok.Getter;

public abstract class PlatformEvent extends Event {
    @Getter
    protected String transform;
}
