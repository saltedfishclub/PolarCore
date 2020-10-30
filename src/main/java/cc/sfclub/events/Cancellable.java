package cc.sfclub.events;

/**
 * A interface that means this event could be cancelled.
 */

public interface Cancellable {

    boolean isCancelled();


    void setCancelled(boolean status);

}
