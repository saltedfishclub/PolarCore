package cc.sfclub.polar.internal.impl.event.operator;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class DeliverOperator<T> extends AbstractChannelOperator<T> {
    private final Consumer<T> consumer;

    @Override
    public void operate(T event) {
        consumer.accept(event);
        super.operate(event);
    }
}
