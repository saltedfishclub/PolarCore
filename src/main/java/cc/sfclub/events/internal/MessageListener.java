package cc.sfclub.events.internal;

import cc.sfclub.events.message.direct.PrivateMessage;
import cc.sfclub.events.message.group.GroupMessage;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public final class MessageListener {
    public static final Set<Consumer<GroupMessage>> gHandlers = new HashSet<>();
    public static final Set<Consumer<PrivateMessage>> mHandlers = new HashSet<>();

    @Subscribe
    public void onMessage(GroupMessage message) {
        gHandlers.forEach(e -> e.accept(message));
    }

    @Subscribe
    public void onMessage(PrivateMessage message) {
        mHandlers.forEach(e -> e.accept(message));
    }
}
