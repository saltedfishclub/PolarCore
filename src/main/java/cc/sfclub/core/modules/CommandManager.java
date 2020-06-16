package cc.sfclub.core.modules;

import cc.sfclub.command.Unknown;
import cc.sfclub.module.Manager;
import cc.sfclub.module.Module;
import lombok.SneakyThrows;

public class CommandManager extends Module {
    private static final CommandManager inst = new CommandManager();
    private static final Unknown FALLBACK = new Unknown();

    @SneakyThrows
    private CommandManager() {
        Manager.getInst().addModule(this);
    }

}
