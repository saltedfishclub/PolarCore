package cc.sfclub.polar.commands;

import cc.sfclub.polar.Command;
import cc.sfclub.polar.CommandBase;
import cc.sfclub.polar.CommandFilter;
import cc.sfclub.polar.CommandManager;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

import java.util.StringJoiner;

@CommandFilter(alias = "aa", provider = "CLI")
@Command(name = "cmds", description = "Get a list of commands", perm = "member.basic.list")
public class List extends CommandBase {
    @Override
    public void onCommand(User u, TextMessage Command) {
        if (Command.getMessage().isEmpty()) {
            StringJoiner str = new StringJoiner("\n", "List of Commands:\n", "");
            CommandManager.getCommandMap().keySet().forEach(s -> {
                if (s.equals("cmds") || s.equals("unknown")) {
                    return;
                }
                str.add(s + " ~ " + CommandManager.getCommandMap().get(s).getDescription());
            });
            Command.getBot().sendMessage(Command, str.toString());
        }

    }
}
