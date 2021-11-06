package cc.sfclub.polar.api.user.perm;

import org.jetbrains.annotations.ApiStatus;

/**
 * Design in progress.
 * @param <P> Perm
 */
@ApiStatus.AvailableSince("5.0.0")
public abstract class PermInitializer<P extends Perm>{
    public abstract Perm initialize(String data);
}
