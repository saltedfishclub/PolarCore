package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;

public abstract class CommandBase {
    @Getter
    public String Perm;
    @Getter
    public String Description;

    public abstract void onCommand(User u, TextMessage Command);

    public String getUsage() {
        return "Try to use \"help\" for some help.";
    }
}
