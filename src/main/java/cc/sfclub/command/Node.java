package cc.sfclub.command;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;

public class Node {

    public static LiteralArgumentBuilder<Source> literal(final String name) {
        return LiteralArgumentBuilder.literal(name);
    }

    public static <T> RequiredArgumentBuilder<Source, T> argument(final String name, final ArgumentType<T> type) {
        return RequiredArgumentBuilder.argument(name, type);
    }

    public static BoolArgumentType boolArg() {
        return BoolArgumentType.bool();
    }

    public static IntegerArgumentType integerArg() {
        return IntegerArgumentType.integer();
    }

    public static DoubleArgumentType doubleArg() {
        return DoubleArgumentType.doubleArg();
    }

    public static FloatArgumentType floatArg() {
        return FloatArgumentType.floatArg();
    }

    public static LongArgumentType longArg() {
        return LongArgumentType.longArg();
    }

    public static StringArgumentType string() {
        return StringArgumentType.string();
    }
}
