package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;

import java.util.ArrayList;

public abstract class CommandBase {
    @Getter
    public String perm;
    @Getter
    public String description;
    public ArrayList<String> aliases = new ArrayList<>();
    public String provider = ".*";

    public abstract void onCommand(User u, TextMessage command);
}
