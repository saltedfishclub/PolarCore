package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.User;
import lombok.Getter;

import java.util.ArrayList;

/**
 * Command Base.
 */
public abstract class CommandBase {
    /**
     * @return permission
     */
    @Getter
    public String perm;
    /**
     * @return Command Name
     */
    @Getter
    public String name;
    /**
     * @return command description
     */
    @Getter
    public String description;
    /**
     * @return aliases
     */
    public ArrayList<String> aliases = new ArrayList<>();
    /**
     * @return provider regex
     */
    public String provider = ".*";

    /**
     * onCommand
     *
     * @param u       user
     * @param command command (cmd head deleted)
     */
    public abstract boolean onCommand(User u, TextMessage command);
}
