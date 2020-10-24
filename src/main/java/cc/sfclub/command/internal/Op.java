package cc.sfclub.command.internal;

import cc.sfclub.command.Source;
import cc.sfclub.user.User;
import cc.sfclub.user.perm.Perm;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class Op implements Command<Source> {
    @Override
    public int run(CommandContext<Source> context) throws CommandSyntaxException {
        User user = User.byUUID(StringArgumentType.getString(context, "user"));
        if (user == null) {
            user = User.byName(StringArgumentType.getString(context, "user"));
        }
        if (user == null) {
            context.getSource().getMessageEvent().reply("User ID/Name not exists.");
            return 0;
        }
        user.addPermission(Perm.of(".*"));
        context.getSource().getMessageEvent().reply("User " + user.asFormattedName() + " is op now.");
        return 0;
    }
}