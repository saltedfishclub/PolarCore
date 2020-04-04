package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;

public class RootCommandTemplate extends CommandBase {
    @Getter
    private ChainCommand chainCommand;

    public RootCommandTemplate(ChainCommand chainCommand) {
        this.chainCommand = chainCommand;
        super.name = chainCommand.getName();
        super.perm = chainCommand.getPerm();
        super.description = chainCommand.getDesc();
    }

    @Override
    public boolean onCommand(User u, TextMessage command) {
        String[] args = command.getMessage().trim().replaceFirst(super.name + " ", "").split(" ");
        if (args.length == 0) return chainCommand.getFallback().onCommand(u, command);
        int round = 0;
        ChainCommand now = chainCommand;
        while (now.checkExists(args[0]) != -1) {
            round++;
            now = now.subChain.get(now.checkExists(args[0]));
            if (round == args.length) {
                if (!now.getCallback().onCommand(u, command)) now.getFallback().onCommand(u, command);
                return true;
            }
        }
        if (!now.getCallback().onCommand(u, command)) now.getFallback().onCommand(u, command);
        return false;
    }
}
