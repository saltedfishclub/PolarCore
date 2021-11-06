package cc.sfclub.polar.api;

import cc.sfclub.polar.api.event.Event;
import cc.sfclub.polar.api.event.IEventChannel;
import cc.sfclub.polar.api.platfrom.IPlatformManager;
import cc.sfclub.polar.api.user.IUserManager;
import cc.sfclub.polar.internal.loader.Loader;

import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;

public interface Bot {
    static Bot getInstance() {
        return Loader.botInst;
    }

    IUserManager getUserManager();

    IPlatformManager getPlatformManager();

    ChannelHolder<Event> getEventChannel();

    Supplier<IEventChannel<Event>> getChannelFactory();

    ExecutorService getCommonPool();
}
