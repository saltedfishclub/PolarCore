package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;

public class RootCommandTemplate extends CommandBase {
    @Getter
    private ChainCommand chainCommand;

    public RootCommandTemplate(ChainCommand chainCommand) {
        this.chainCommand = chainCommand;
        super.name = chainCommand.name;
        super.perm = chainCommand.perm;
        super.description = chainCommand.desc;
    }

    @Override
    public boolean onCommand(User u, TextMessage command) {
        String[] args = command.getMessage().trim().replaceFirst(super.name + " ", "").split(" ");
        if (args.length == 0) return chainCommand.fallback.onCommand(u, command);
        int round = 0;
        ChainCommand now = chainCommand;
        while (now.checkExists(args[0]) != -1) {
            round++;
            now = now.subChain.get(now.checkExists(args[0]));
            if (round == args.length) {
                if (!now.callback.onCommand(u, command)) now.fallback.onCommand(u, command);
                return true;
            }
        }
        if (!now.callback.onCommand(u, command)) now.fallback.onCommand(u, command);
        return false;
    }
}
