package cc.sfclub.polar.internal.impl.event;

import cc.sfclub.polar.api.event.Event;
import cc.sfclub.polar.api.event.IEventChannel;
import cc.sfclub.polar.internal.impl.event.operator.*;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class EventChannelImpl<T> implements IEventChannel<T> {
    private final ChannelOperator<T> operator = new NothingOperator<>();
    private boolean disposed=false;
    @Override
    public void publish(T event) {
        operator.operate(event);
    }

    @Override
    public void dispose() {
        disposed=true;
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }

    @Override
    public IEventChannel<T> filter(Predicate<T> predicate) {
        operator.setNext(new FilterOperator<>(predicate));
        return this;
    }

    @Override
    public <R extends Event> IEventChannel<R> forEvent(Class<R> classOfEvent) {
        return filter(classOfEvent::isInstance).map(classOfEvent::cast);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> IEventChannel<R> map(Function<T, R> mapper) {
        operator.setNext(new MapOperator<>(mapper));
        return (IEventChannel<R>) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> IEventChannel<R> flatMap(Function<? super T, ? extends IEventChannel<? extends R>> mapper) {
        operator.setNext(new FlatMapOperator<>(mapper));
        return (IEventChannel<R>) this;
    }

    @Override
    public IEventChannel<T> receive(Consumer<T> listener) {
        operator.setNext(new DeliverOperator<>(listener));
        return this;
    }

}
