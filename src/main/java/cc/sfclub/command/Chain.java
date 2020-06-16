package cc.sfclub.command;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Chain { //todo use brigadier
    public static ICommand DEFAULT_FALLBACK;
    public List<Chain> subChain;
    @Getter
    private String perm;
    @Getter
    private String name;
    @Getter
    private String desc;
    @Getter
    private ICommand callback;
    @Setter
    @Getter
    private ICommand fallback = DEFAULT_FALLBACK;

    private Chain() {
    }

    public Chain(String cmd, String perm, ICommand callback) {
        this.name = cmd;
        this.callback = callback;
        this.perm = perm;
    }

    public Chain(String cmd, String perm) {
        this.name = cmd;
        this.callback = fallback;
        this.perm = perm;
    }

    public static Chain build(String cmd, String perm, ICommand callback) {
        return new Chain(cmd, perm, callback);
    }

    public static Chain build(String cmd, String perm) {
        return new Chain(cmd, perm);
    }

    public static Chain build(String cmd) {
        return new Chain(cmd, "");
    }

    public Chain branch(Chain chainCommand) {
        Chain chain = chainCommand;
        if (chain.perm.isEmpty()) {
            chain.perm = perm;
        }
        subChain.add(chain);
        return this;
    }

    public Chain fallback(ICommand fb) {
        this.fallback = fb;
        return this;
    }

    public Chain description(String desc) {
        this.desc = desc;
        return this;
    }

    public int checkExists(String sub) {
        AtomicInteger state = new AtomicInteger(-1);
        subChain.forEach(c -> {
            if (c.name.equalsIgnoreCase(sub)) {
                state.set(subChain.indexOf(c));
            }
        });
        return state.get();
    }

    public Chain execute(ICommand executor) {
        this.callback = executor;
        return this;
    }
}
