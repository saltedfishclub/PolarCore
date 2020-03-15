package cc.sfclub.polar.wrapper;

import cc.sfclub.polar.Core;

public final class SimpleWrapper extends Bot {

    @Override
    public String getPlatfrom() {
        return "CLI";
    }

    @Override
    public long sendMessage(long id, String message) {
        Core.getLogger().info("ID: {} Msg: {}", id, message);
        return 0L;
    }
}
