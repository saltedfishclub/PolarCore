package cc.sfclub.polar.commands;

import cc.sfclub.polar.Command;
import cc.sfclub.polar.CommandBase;
import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

@Command(name = "ver", description = "get version info", perm = "member.basic.version")
public class Version extends CommandBase {
    @Override
    public void onCommand(User u, TextMessage Command) {
        Core.getBot(Command).sendMessage(Command, new String[]{
                Core.getConf().name + " v" + Core.getConf().config_version,
                "PolarCore by iceBear67",
                "Made with THE STRONGEST HATE"
        });
    }
}
