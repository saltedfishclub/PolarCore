package cc.sfclub.command.internal;

import cc.sfclub.command.Source;
import cc.sfclub.user.User;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;

import java.util.Arrays;

public class Me implements Command<Source> {
    @Override
    public int run(CommandContext<Source> context) {
        Source src = context.getSource();
        User u = src.getSender();
        src.getMessageEvent().reply("Your UserID: " + u.getUniqueID() + "\n" +
                "Your UserGroup: " + u.getUserGroup() + "\n" +
                "Redirected User: " + (u.getRedirectTo() != null) + "\n" +
                "Permissions(" + u.permList.size() + "): " + Arrays.toString(u.permList.toArray())
        );
        return 0;
    }
}
