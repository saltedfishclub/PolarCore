package cc.sfclub.command.internal;

import cc.sfclub.command.Source;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class Me implements Command<Source> {
    @Override
    public int run(CommandContext<Source> context) throws CommandSyntaxException {
        Source src = context.getSource();
        src.getMessageEvent().reply("Your UserID: " + src.getSender());
        return 0;
    }
}
