package cc.sfclub.polar.commands;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

public class Version extends CommandBase {
    @Override
    public String getDescription() {
        return "Get Version Info";
    }

    @Override
    public String getPerm() {
        return "member.basic.cmd.version";
    }

    @Override
    public void onCommand(User u, TextMessage Command) {
        Core.getBot(Command).sendMessage(Command, Core.getConf().name + " v" + Core.getConf().config_version);
    }
}
