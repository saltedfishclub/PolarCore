package cc.sfclub.polar.commands;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

public abstract class CommandBase {
    public abstract String getDescription();

    public abstract String getPerm();

    public abstract void onCommand(User u, TextMessage Command);
}
