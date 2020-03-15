package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;

import java.util.concurrent.Callable;

public class LoadCallback implements Callable<Integer> {
    TextMessage tm;

    public LoadCallback(TextMessage tm) {
        this.tm = tm;
    }

    @Override
    public Integer call() throws Exception {
        tm.reply("PolarCore Reloaded!!");
        return 0;
    }
}
