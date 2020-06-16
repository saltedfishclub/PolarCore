package cc.sfclub.events.message;

import cc.sfclub.events.Cancellable;
import cc.sfclub.events.Event;
import cc.sfclub.util.Since;
import lombok.Getter;

/**
 * Event about message
 */
@Since("4.0")
public class MessageEvent extends Event implements Cancellable {
    @Getter
    private final String UserID;
    @Getter
    private final String message;
    private boolean cancelled = false;

    public MessageEvent(String userID, String message) {
        this.UserID = userID;
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
