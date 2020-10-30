package cc.sfclub.core;

import cc.sfclub.command.CommandListener;
import cc.sfclub.command.Source;
import cc.sfclub.command.internal.Me;
import cc.sfclub.command.internal.Op;
import cc.sfclub.events.Event;
import cc.sfclub.events.message.group.GroupMessage;
import cc.sfclub.events.server.ServerStartedEvent;
import cc.sfclub.events.server.ServerStoppingEvent;
import cc.sfclub.transform.internal.ConsoleBot;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.util.common.SimpleFile;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Scanner;

import static com.mojang.brigadier.arguments.StringArgumentType.string;

public class Initializer {
    private static final CoreCfg cfg = (CoreCfg) new CoreCfg().saveDefaultOrLoad();
    private static final Logger logger = LoggerFactory.getLogger(Initializer.class);
    @SneakyThrows
    public static void main(String[] args) {
        loadLang();
        System.out.println("\n" + "________      ______             _________                  \n" +
                "___  __ \\________  /_____ _________  ____/_________________ \n" +
                "__  /_/ /  __ \\_  /_  __ `/_  ___/  /    _  __ \\_  ___/  _ \\\n" +
                "_  ____// /_/ /  / / /_/ /_  /   / /___  / /_/ /  /   /  __/\n" +
                "/_/     \\____//_/  \\__,_/ /_/    \\____/  \\____//_/    \\___/ \n" +
                "                                                            \n");
        logger.info(I18N.get().server.STARTING, Core.CORE_VERSION);
        logger.info(I18N.get().server.LOADING_MODULES);
        loadCore();
        Core.get().registerBot(new ConsoleBot());
        loadPlugins();
        logger.info(I18N.get().server.LOADED_MODULE);
        Event.postEvent(new ServerStartedEvent());
        waitCommand();
    }
    private static void waitCommand() {
        Scanner scanner = new Scanner(System.in);
        Event.registerListeners(new CommandListener());
        Core.get().dispatcher().register(LiteralArgumentBuilder.<Source>literal("me").executes(new Me()));
        Core.get().dispatcher().register(LiteralArgumentBuilder.<Source>literal("op")
                .requires(e -> Core.get().userManager().byUUID(e.getSender()).hasPermission(Perm.of("internal.op")))
                .then(RequiredArgumentBuilder.<Source, String>argument("user", string()).executes(new Op()))
        );
        String command;
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if ("stop".equals(command)) {
                logger.info(I18N.get().server.STOPPING_SERVER);
                Event.postEvent(new ServerStoppingEvent());
                System.exit(0);
            }
            Event.postEvent(new GroupMessage(Core.get().console().getUniqueID(), command, 0L, "CONSOLE", 0L));
        }
        scanner.close();
    }

    @SneakyThrows
    @SuppressWarnings("all")
    private static void loadPlugins() {
        //todo
    }

    @SneakyThrows
    private static void loadCore() {
        //Load Configs
        boolean firstLoad = false;
        DatabaseCfg dbCfg = new DatabaseCfg();
        PermCfg permCfg = new PermCfg();
        if (!SimpleFile.exists(permCfg.getConfigName()) || !SimpleFile.exists(dbCfg.getConfigName())) {
            firstLoad = true;
        }
        dbCfg = (DatabaseCfg) dbCfg.saveDefaultOrLoad();
        permCfg = (PermCfg) permCfg.saveDefaultOrLoad();
        if (firstLoad) {
            logger.info(I18N.get().server.FIRST_START);
            System.exit(0);
        }
        if (cfg.getConfig_version() != Core.CONFIG_VERSION)
            logger.warn(I18N.get().exceptions.CONFIG_OUTDATED, cfg.getConfigName());
        Core.setDefaultCore(new Core(cfg, permCfg, dbCfg));
    }

    private static void loadLang() {
        new File("locale").mkdir();
        I18N i18N = new I18N(cfg.getLocale());
        I18N.setInst((I18N) i18N.saveDefaultOrLoad());
        if (i18N.getConfVer() != I18N.CONFIG_VERSION) {
            logger.warn(I18N.get().exceptions.CONFIG_OUTDATED, cfg.getConfigName());
        }
    }

}
