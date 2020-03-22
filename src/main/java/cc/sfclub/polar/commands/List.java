package cc.sfclub.polar.commands;

import cc.sfclub.polar.Command;
import cc.sfclub.polar.CommandBase;
import cc.sfclub.polar.CommandFilter;
import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

import java.util.StringJoiner;

@CommandFilter(provider = "CLI")
@Command(name = "cmds", description = "Get a list of commands", perm = "member.basic.list")
public class List extends CommandBase {
    @Override
    public void onCommand(User u, TextMessage command) {
        if (command.getMessage().isEmpty()) {
            StringJoiner str = new StringJoiner("\n", "List of Commands:\n", "");
            Core.getInstance().getCommandManager().getCommandMap().keySet().forEach(s -> {
                if ("cmds".equals(s) || "unknown".equals(s)) {
                    return;
                }
                str.add(s + " ~ " + Core.getInstance().getCommandManager().getCommandMap().get(s).getDescription());
            });
            command.reply(str.toString());
        }

    }
}
