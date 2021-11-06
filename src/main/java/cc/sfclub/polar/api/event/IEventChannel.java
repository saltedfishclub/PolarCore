package cc.sfclub.polar.api.event;


import org.jetbrains.annotations.NotNull;

import java.util.function.*;
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface IEventChannel<T> {
    void publish(T event);

    void dispose();

    boolean isDisposed();

    IEventChannel<T> filter(Predicate<T> predicate);

    <R extends Event> IEventChannel<R> forEvent(Class<R> classOfEvent);

    <R> IEventChannel<R> map(Function<T,R> mapper);

    <R> IEventChannel<R> flatMap(Function<? super T, ? extends IEventChannel<? extends R>> mapper);

    IEventChannel<T> receive(Consumer<T> listener);

}
