package cc.sfclub.polar.internal.impl.event.operator;

import cc.sfclub.polar.api.Bot;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class AsyncDeliverOperator<T> extends AbstractChannelOperator<T>{
    private final Consumer<T> consumer;

    @Override
    public void operate(T event) {
        Bot.getInstance().getCommonPool().submit(()->{consumer.accept(event);});
        super.operate(event);
    }
}
