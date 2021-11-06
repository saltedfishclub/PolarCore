package cc.sfclub.polar.api.event;

/**
 * 表示一个事件监听器，会被 SPI 发掘并且注册。
 */
@FunctionalInterface
public interface ListenerHost<E extends Event> {
    boolean onEvent(E event);
}
