package cc.sfclub.events;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean status);

}
