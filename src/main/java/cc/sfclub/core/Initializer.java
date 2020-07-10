package cc.sfclub.core;

import cc.sfclub.command.CommandListener;
import cc.sfclub.events.message.group.GroupMessageReceivedEvent;
import cc.sfclub.events.server.ServerStartedEvent;
import cc.sfclub.events.server.ServerStoppingEvent;
import cc.sfclub.plugin.Plugin;
import cc.sfclub.plugin.PluginManager;
import cc.sfclub.transform.internal.ConsoleBot;
import cc.sfclub.user.Group;
import cc.sfclub.user.User;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.util.common.SimpleFile;
import com.alibaba.druid.pool.DruidDataSource;
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
        String command;
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if ("stop".equals(command)) {
                Core.getLogger().info(I18N.get().server.STOPPING_SERVER);
                EventBus.getDefault().post(new ServerStoppingEvent());
                break;
            }
            EventBus.getDefault().post(new GroupMessageReceivedEvent(Core.get().console().getUniqueID(), command, 0L, "CONSOLE", 0L));
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
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbCfg.getJdbcUrl());
        dataSource.setUsername(dbCfg.getUser());
        dataSource.setPassword(dbCfg.getPassword());
        if (cfg.getConfig_version() != Core.CONFIG_VERSION)
            Core.getLogger().warn(I18N.get().exceptions.CONFIG_OUTDATED, cfg.getConfigName());
        Core.setCore(new Core(cfg, permCfg, dataSource));
        if (!Core.get().ORM().exists(User.class)) {
            Core.getLogger().warn(I18N.get().exceptions.TABLE_NOT_FOUND, User.class.getName());
            Core.get().ORM().create(User.class, false);
            User console = new User(null, new Perm(".*"));
            console.setUserName("CONSOLE");
            Core.get().ORM().insert(console);
        }
        Core.get().loadConsole();
        if (!Core.get().ORM().exists(Group.class)) {
            Core.getLogger().warn(I18N.get().exceptions.TABLE_NOT_FOUND, Group.class.getName());
            Core.get().ORM().create(Group.class, false);
            permCfg.getGroupList().forEach(Core.get().ORM()::insert);
        }
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
