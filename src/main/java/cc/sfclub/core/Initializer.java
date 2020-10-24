package cc.sfclub.core;

import cc.sfclub.command.CommandListener;
import cc.sfclub.command.Source;
import cc.sfclub.command.internal.Me;
import cc.sfclub.events.message.group.GroupMessage;
import cc.sfclub.events.server.ServerStartedEvent;
import cc.sfclub.events.server.ServerStoppingEvent;
import cc.sfclub.plugin.Plugin;
import cc.sfclub.plugin.PluginManager;
import cc.sfclub.transform.internal.ConsoleBot;
import cc.sfclub.util.common.SimpleFile;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.*;
import java.util.jar.JarFile;

public class Initializer {
    private static final CoreCfg cfg = (CoreCfg) new CoreCfg().saveDefaultOrLoad();
    @SneakyThrows
    public static void main(String[] args) {
        loadLang();
        System.out.println("\n" + "________      ______             _________                  \n" +
                "___  __ \\________  /_____ _________  ____/_________________ \n" +
                "__  /_/ /  __ \\_  /_  __ `/_  ___/  /    _  __ \\_  ___/  _ \\\n" +
                "_  ____// /_/ /  / / /_/ /_  /   / /___  / /_/ /  /   /  __/\n" +
                "/_/     \\____//_/  \\__,_/ /_/    \\____/  \\____//_/    \\___/ \n" +
                "                                                            \n");
        Core.getLogger().info(I18N.get().server.STARTING, Core.CORE_VERSION);
        Core.getLogger().info(I18N.get().server.LOADING_MODULES);
        loadCore();
        Core.get().registerBot(new ConsoleBot());
        loadPlugins();
        Core.getLogger().info(I18N.get().server.LOADED_MODULE);
        EventBus.getDefault().post(new ServerStartedEvent());
        waitCommand();
    }
    private static void waitCommand() {
        Scanner scanner = new Scanner(System.in);
        EventBus.getDefault().register(new CommandListener());
        Core.get().dispatcher().register(LiteralArgumentBuilder.<Source>literal("me").executes(new Me()));
        String command;
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if ("stop".equals(command)) {
                Core.getLogger().info(I18N.get().server.STOPPING_SERVER);
                EventBus.getDefault().post(new ServerStoppingEvent());
                System.exit(0);
            }
            EventBus.getDefault().post(new GroupMessage(Core.get().console().getUniqueID(), command, 0L, "CONSOLE", 0L));
        }
        scanner.close();
    }

    @SneakyThrows
    @SuppressWarnings("all")
    private static void loadPlugins() {
        File plugins = new File("./plugins");
        if (!plugins.exists()) {
            plugins.mkdir();
        }
        HashMap<String, List<String>> depended = new HashMap<>();
        for (File _plugin : Objects.requireNonNull(plugins.listFiles())) {
            if (!_plugin.getName().endsWith(".jar")) {
                continue;
            }
            JarFile jarFile = new JarFile(_plugin);
            Optional<Plugin> plugin = PluginManager.getInst().loadPlugin(_plugin, jarFile);
            String dependencies = jarFile.getManifest().getMainAttributes().getValue("Plugin-Depend");
            plugin.ifPresent(p -> {
                if (dependencies == null) return;
                String[] depends = dependencies.split(",");
                if (depends.length < 1) {
                    Core.getLogger().warn(I18N.get().exceptions.PLUGIN_DEPENDS_ILLEGAL, p.getDescription().getName());
                }
                for (String depend : depends) {
                    if (!depended.containsKey(depend)) {
                        depended.put(depend, Collections.singletonList(p.getDescription().getName()));
                        continue;
                    }
                    List<String> names = depended.get(depend);
                    names.add(p.getDescription().getName());
                    depended.put(depend, names);
                }
            });
        }
        //Check dependencies
        depended.forEach((k, v) -> {
            if (PluginManager.getInst().isLoaded(k)) {
                Core.getLogger().warn(I18N.get().exceptions.PLUGIN_DEPENDS_ILLEGAL, "");
                v.forEach(p -> Core.getLogger().warn("- {}", p));
            }
        });
        Core.getLogger().info(I18N.get().misc.LOADED_PLUGINS, PluginManager.getInst().getPlugins().size(), PluginManager.getInst().getPluginNames());
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
            Core.getLogger().info(I18N.get().server.FIRST_START);
            System.exit(0);
        }
        if (cfg.getConfig_version() != Core.CONFIG_VERSION)
            Core.getLogger().warn(I18N.get().exceptions.CONFIG_OUTDATED, cfg.getConfigName());
        Core.setDefaultCore(new Core(cfg, permCfg, dbCfg));
        Core.get().loadDatabase();
    }

    private static void loadLang() {
        new File("locale").mkdir();
        I18N i18N = new I18N(cfg.getLocale());
        I18N.setInst((I18N) i18N.saveDefaultOrLoad());
        if (i18N.getConfVer() != I18N.CONFIG_VERSION) {
            Core.getLogger().warn(I18N.get().exceptions.CONFIG_OUTDATED, cfg.getConfigName());
        }
    }

}
