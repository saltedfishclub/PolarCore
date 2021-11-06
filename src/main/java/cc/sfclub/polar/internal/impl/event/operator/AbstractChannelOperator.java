package cc.sfclub.polar.internal.impl.event.operator;

public abstract class AbstractChannelOperator<T> implements ChannelOperator<T>{
    protected ChannelOperator<T> next;

    @Override
    public void operate(T event) {
        if (next != null) {
            next.operate(event);
        }
    }

    @Override
    public void setNext(ChannelOperator<T> operator) {
        if (next != null) {
            next.setNext(operator);
            return;
        }
        next = operator;
    }
}
