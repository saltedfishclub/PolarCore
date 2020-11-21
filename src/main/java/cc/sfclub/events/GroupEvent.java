package cc.sfclub.events;

import lombok.Getter;

public abstract class GroupEvent extends PlatformEvent {
    @Getter
    protected long groupId;
}
