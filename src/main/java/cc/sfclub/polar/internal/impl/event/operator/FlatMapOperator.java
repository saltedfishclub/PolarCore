package cc.sfclub.polar.internal.impl.event.operator;

import cc.sfclub.polar.api.event.IEventChannel;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public class FlatMapOperator<T,R> extends AbstractChannelOperator<T>{
    private final Function<? super T, ? extends IEventChannel<? extends R>> mapper;
    protected ChannelOperator<R> next;

    @Override
    @SuppressWarnings("unchecked")
    public void operate(T event) {
        if(next!=null)next.operate((R) mapper.apply(event));
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
