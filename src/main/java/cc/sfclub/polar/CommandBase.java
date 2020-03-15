package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;

import java.util.ArrayList;

public abstract class CommandBase {
    @Getter
    public String Perm;
    @Getter
    public String Description;
    public ArrayList<String> Aliases = new ArrayList<>();
    public String Provider = ".*";
    public abstract void onCommand(User u, TextMessage Command);
}
