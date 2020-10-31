package cc.sfclub.command.internal;

import cc.sfclub.command.Source;
import cc.sfclub.core.Core;
import cc.sfclub.core.Initializer;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class Stop implements Command<Source> {
    @Override
    public int run(CommandContext<Source> context) throws CommandSyntaxException {
        if (Initializer.getCONSOLE().getUniqueID().equals(context.getSource().getSender().getUniqueID())) {
            Core.get().stop();
            System.exit(0);
        }
        return 0;
    }
}
