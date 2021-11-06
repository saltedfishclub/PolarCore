package cc.sfclub.polar.api;

import cc.sfclub.polar.api.event.IEventChannel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class ChannelHolder<T> {
    private final Supplier<IEventChannel<? super T>> channelFactory;
    private List<IEventChannel<? super T>> subscribers = new ArrayList<>();
    public IEventChannel<? super T> open(){
        var chan = channelFactory.get();
        subscribers.add(chan);
        return chan;
    }

    public <R> IEventChannel<? extends R> openFor(Class<R> tClass){
        return open().filter(tClass::isInstance).map(tClass::cast);
    }

    public void publish(T event){
        subscribers.removeIf(e->{
            if(e.isDisposed())return true;
            e.publish(event);
            return false;
        });
    }
}
