package cc.sfclub.events;

import cc.sfclub.util.Since;

/**
 * A interface that means could be cancelled.
 */
@Since("4.0")
public interface Cancellable {
    @Since("4.0")
    boolean isCancelled();

    @Since("4.0")
    void setCancelled(boolean status);

}
