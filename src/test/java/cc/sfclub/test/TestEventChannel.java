package cc.sfclub.test;

import cc.sfclub.polar.api.event.IEventChannel;
import cc.sfclub.polar.internal.impl.event.EventChannelImpl;
import org.junit.Test;

public class TestEventChannel {
    @Test
    public void onTest(){
        IEventChannel<String> ec = new EventChannelImpl<>();
        ec.map(String::toLowerCase).filter(e->e.startsWith("ac")).receive(e-> {
            assert e.equals("accd");
        });
        ec.publish("BACD");
        ec.publish("abcd");
        ec.publish("AcCD");


    }
}
