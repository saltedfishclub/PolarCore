package cc.sfclub.transform;

import cc.sfclub.util.Since;

@Since("4.0")
public interface Transform {
    @Since("4.0")
    String getName();

    @Since("4.0")
    Bot getProvider();
}
