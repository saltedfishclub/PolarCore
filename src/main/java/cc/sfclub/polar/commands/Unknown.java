package cc.sfclub.polar.commands;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

public class Unknown extends CommandBase {
    @Override
    public String getDescription() {
        return "fallback";
    }

    @Override
    public String getPerm() {
        return "member.basic.unknown";
    }

    @Override
    public void onCommand(User u, TextMessage Command) {
        Core.getBot(Command).sendMessage(Command, "Wrong usage or unknown command.(Maybe you can try \"help\" for help)");
    }
}
