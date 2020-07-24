package cc.sfclub.events;

import lombok.Getter;

/**
 * Event about message
 */

public class MessageEvent extends Event implements Cancellable {
    @Getter
    private final String UserID;
    @Getter
    private final String message;
    @Getter
    private final long messageID;
    @Getter
    private final String transform;
    private boolean cancelled = false;

    public MessageEvent(String userID, String message, String transform, long messageID) {
        this.UserID = userID;
        this.message = message;
        this.transform = transform;
        this.messageID = messageID;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean status) {
        cancelled = status;
    }

    public void reply(long msgId, String message) {
        throw new IllegalArgumentException("REPLY IS NOT SUPPORTED");
    }
}
