package cc.sfclub.events;

/**
 * A interface that means could be cancelled.
 */

public interface Cancellable {

    boolean isCancelled();


    void setCancelled(boolean status);

}
