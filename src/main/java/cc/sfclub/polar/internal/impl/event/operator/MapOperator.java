package cc.sfclub.polar.internal.impl.event.operator;

import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class MapOperator<T,R> extends AbstractChannelOperator<T>{
    private final Function<T,R> mapper;
    protected ChannelOperator<R> next;

    @Override
    public void operate(T event) {
        if(next!=null)next.operate(mapper.apply(event));
    }
    @Override
    @SuppressWarnings("unchecked")
    public void setNext(ChannelOperator<T> operator) {
        if (next != null) {
            next.setNext((ChannelOperator<R>) operator);
            return;
        }
        next = (ChannelOperator<R>) operator;
    }
}
