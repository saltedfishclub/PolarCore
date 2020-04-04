package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChainCommand {
    public static Executor DEFAULT_FALLBACK = (u, m) -> {
        CommandManager.FALLBACK.onCommand(u, m);
        return true;
    };
    public List<ChainCommand> subChain;
    @Getter
    private String perm;
    @Getter
    private String name;
    @Getter
    private String desc;
    @Getter
    private Executor callback;
    @Setter
    @Getter
    private Executor fallback = DEFAULT_FALLBACK;

    public ChainCommand(String cmd, String perm, CommandBase callback) {
        this.name = cmd;
        this.callback = callback::onCommand;
        this.perm = perm;
    }

    public ChainCommand(String cmd, String perm) {
        this.name = cmd;
        this.callback = fallback;
        this.perm = perm;
    }

    public ChainCommand then(String cmd, String perm, CommandBase callback) {
        ChainCommand chainCommand = new ChainCommand(cmd, perm, callback);
        subChain.add(chainCommand);
        return chainCommand;
    }

    public ChainCommand then(String cmd, String perm) {
        ChainCommand chainCommand = new ChainCommand(cmd, perm);
        subChain.add(chainCommand);
        return chainCommand;
    }

    public ChainCommand branch(ChainCommand chainCommand) {
        subChain.add(chainCommand);
        return this;
    }

    public ChainCommand fallback(Executor fb) {
        this.fallback = fb;
        return this;
    }

    public ChainCommand description(String desc) {
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

    public ChainCommand execute(Executor executor) {
        this.callback = executor;
        return this;
    }

    public ChainCommand execute(CommandBase executor) {
        this.callback = executor::onCommand;
        return this;
    }

    @FunctionalInterface
    public interface Executor {
        boolean onCommand(User u, TextMessage msg);
    }
}
