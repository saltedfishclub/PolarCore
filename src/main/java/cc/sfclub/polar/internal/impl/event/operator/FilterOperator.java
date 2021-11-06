package cc.sfclub.polar.internal.impl.event.operator;

import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class FilterOperator<T> extends AbstractChannelOperator<T> {
    private final Predicate<T> predicate;

    @Override
    public void operate(T event) {
        if (predicate.test(event) && next != null) next.operate(event);
    }
}
