package cc.sfclub.polar.internal.impl;

import cc.sfclub.polar.api.Bot;
import cc.sfclub.polar.api.ChannelHolder;
import cc.sfclub.polar.api.event.Event;
import cc.sfclub.polar.api.event.IEventChannel;
import cc.sfclub.polar.api.platfrom.IPlatformManager;
import cc.sfclub.polar.api.user.IUserManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Getter
public class PolarBot implements Bot {
    private final IUserManager userManager;
    private final IPlatformManager platformManager;
    private final ChannelHolder<Event> eventChannel;
    private final Supplier<IEventChannel<Event>> channelFactory;
    private final ExecutorService commonPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
}
