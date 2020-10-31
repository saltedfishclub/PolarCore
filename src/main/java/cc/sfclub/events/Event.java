package cc.sfclub.events;

import cc.sfclub.core.Core;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 事件基类/控制相关
 */

public abstract class Event {
    private static final Logger logger = LoggerFactory.getLogger(Event.class);
    private static EventBus eventBus = EventBus.builder()
            .eventInheritance(true)
            .sendNoSubscriberEvent(false)
            .throwSubscriberException(true)
            .build();

    public static void registerListeners(Object... object) {
        for (Object o : object) {
            eventBus.register(o);
        }
    }

    public static void unregisterListeners(Object... object) {
        eventBus.unregister(object);
    }

    private static void initEventBus() {
        EventBus.builder()
                .eventInheritance(true)
                .sendNoSubscriberEvent(false)
                .throwSubscriberException(true)
                .build();
    }

    public static void unregisterAllListeners() {
        eventBus = null;
        initEventBus();
    }

    public static void postEvent(Event event) {
        eventBus.post(event);
    }

    public static void postEventSticky(Event event) {
        eventBus.postSticky(event);
    }

    public static void setCancelled(Event event) {
        eventBus.cancelEventDelivery(event);
    }

    public static void broadcastMessage(MessageEvent event, long time) {
        Core.get().getPolarSec().postMessage(event, time);
    }
}
