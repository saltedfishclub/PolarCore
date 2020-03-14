package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;

public abstract class CommandBase {
    public String Perm;

    public String getDescription() {
        return "";
    }

    public String getPerm() {
        return Perm;
    }

    public abstract void onCommand(User u, TextMessage Command);

    public String getUsage() {
        return "Try to use \"help\" for some help.";
    }
}
