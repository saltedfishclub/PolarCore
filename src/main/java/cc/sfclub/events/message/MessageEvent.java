package cc.sfclub.events.message;

import cc.sfclub.events.Cancellable;
import cc.sfclub.events.Event;
import lombok.Getter;

public class MessageEvent extends Event implements Cancellable {
    @Getter
    private final String UserID;
    @Getter
    private final String transform;
    @Getter
    private final String message;
    private boolean cancelled = false;

    public MessageEvent(String userID, String transform, String message) {
        this.UserID = userID;
        this.transform = transform;
        this.message = message;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean status) {
        cancelled = status;
    }
}
