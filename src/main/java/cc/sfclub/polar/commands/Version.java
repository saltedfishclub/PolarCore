package cc.sfclub.polar.commands;

import cc.sfclub.polar.Command;
import cc.sfclub.polar.CommandBase;
import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

@Command(name = "ver", description = "get version info", perm = "member.basic.version")
public class Version extends CommandBase {
    @Override
    public void onCommand(User u, TextMessage command) {
        command.reply(new String[]{
                Core.getConf().name + " " + Core.getConf().version,
                "PolarCore by iceBear67",
                "https://github.com/saltedfishclub/PolarCore"
        });
    }
}
