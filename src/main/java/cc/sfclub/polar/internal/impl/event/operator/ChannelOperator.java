package cc.sfclub.polar.internal.impl.event.operator;

public interface ChannelOperator<T> {
    void operate(T event);
    void setNext(ChannelOperator<T> operator);
}
