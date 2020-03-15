package cc.sfclub.polar;

import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.user.Group;
import cc.sfclub.polar.user.User;
import cc.sfclub.polar.utils.PermUtil;
import cc.sfclub.polar.utils.UserUtil;
import cc.sfclub.polar.wrapper.Bot;
import cc.sfclub.polar.wrapper.SimpleWrapper;
import com.alibaba.druid.pool.DruidDataSource;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.greenrobot.eventbus.EventBus;
import org.mve.plugin.PluginException;
import org.mve.plugin.PluginManager;
import org.mve.plugin.java.JavaPlugin;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Core {
    private static final int CONFIG_VERSION = 6;
    @Getter
    private static EventBus Message = EventBus.getDefault();
    @Getter
    private static final Logger logger = LoggerFactory.getLogger(Core.class);
    @Getter
    private static Config conf;
    @Getter
    private static PermUtil PermManager;
    @Getter
    private static PluginManager PluginManager = new PluginManager();
    private static DruidDataSource DataSource = new DruidDataSource();
    @Getter
    private static Dao dao;
    @Getter
    private static UserUtil UserManager;
    @Getter
    private static Gson Gson = new Gson();
    @Getter
    private static HashMap<String, Bot> Wrappers = new HashMap<>();
    @Getter
    private static CommandManager CommandManager = new CommandManager();
    @Getter
    private static String DefaultGroup;
    @Getter
    private static ArrayList<JavaPlugin> plugins = new ArrayList<>();
    private static boolean loaded = false;
    @Setter
    private static LoadCallback cb;

    public static void main(String[] args) {
        init();
        waitCommand();
        System.exit(0);
    }

    public static void init() {
        logger.info("Loading Config");
        loadConfig();
        if (!loaded) {
            logger.info("Loading Databases");
            loadDatabase();
            loaded = true;
        }
        logger.info("Loading User and Perm");
        PermManager = new PermUtil();
        UserManager = new UserUtil();
        logger.info("Loading Events");
        loadEventBus();
        logger.info("Loading CommandManager");
        cc.sfclub.polar.CommandManager.getCommandMap().clear();
        CommandManager.register(new Reflections("cc.sfclub.polar"));
        Wrappers.clear();
        logger.info("Loading Plugins");
        PluginManager.getPlugins().clear();
        loadPlugins();
        addBot(new SimpleWrapper());
        logger.info("All-Completed.");
        if (cb != null) {
            try {
                cb.call();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Failed to CallBack(SPM Notify)");
            }
        }
        System.gc();
    }

    private static void loadPlugins() {
        plugins.clear();
        System.gc();
        File a = new File("./plugins");
        if (!a.exists()) {
            if (!a.mkdir()) {
                Core.getLogger().error("Could not create plugins folder.");
            }
            new File("./plugins/config").mkdir();
            return;
        }
        ArrayList<File> fs = new ArrayList<>();
        for (File file : a.listFiles()) {
            if (!file.isDirectory()) {
                try {
                    JavaPlugin jp = PluginManager.loadPlugin(file);
                    if (jp != null) {
                        PluginManager.enablePlugin(jp);
                        plugins.add(jp);
                    }
                } catch (PluginException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void loadDatabase() {
        DataSource.setUrl("jdbc:mysql://" + conf.database.host + "/" + conf.database.database);
        DataSource.setUsername(conf.database.user);
        DataSource.setPassword(conf.database.password);
        dao = new NutDao(DataSource);
        if (!dao.exists("user")) {
            logger.info("Creating Table: user");
            dao.create(User.class, false);
            User a = new User(0L, "CLI", "OPERATOR");
            a.UserName = "CONSOLE";
            logger.info("Added User: CONSOLE");
            dao.insert(a);
        }
        logger.info("{} User loaded!", dao.count("user"));
        if (!dao.exists("perm")) {
            dao.create(Group.class, false);
            conf.groups.forEach(group -> dao.insert(group));
        }
        logger.info("{} Groups loaded", dao.count("perm"));
    }

    private static void loadEventBus() {
        Message.unregister(CommandManager);
        Message.register(CommandManager);
    }

    private static void waitCommand() {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String command;
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if (command.equals("stop")) {
                logger.info("Stopping Server...");
                plugins.forEach(PluginManager::disablePlugin);
                break;
            }
            Message.post(new TextMessage("CLI", i, 0L, command, 0));
            i++;
        }
        scanner.close();
    }

    private static void loadConfig() {
        File config = new File("config.json");
        if (!config.exists()) {
            conf = new Config();
            conf.config_version = CONFIG_VERSION;
            conf.debug = false;
            conf.name = "Polar";
            conf.version = "debugging-config-required";
            conf.database.host = "172.17.0.3";
            conf.database.password = "123456";
            conf.database.user = "root";
            conf.database.database = "polar";
            conf.startsWith = "!";
            //
            Group op = new Group();
            op.pGroup = "OPERATOR";
            op.nodes.add(".*");
            op.internalID = 0L;
            Group mod = new Group();
            mod.pGroup = "MODERATOR";
            mod.nodes.add("member.mod.*");
            mod.nodes.add("member.basic.*");
            mod.extend = "MEMBER";
            Group member = new Group();
            member.pGroup = "MEMBER";
            member.nodes.add("member.basic.*");
            member.isDefault = true;
            //
            conf.groups.add(op);
            conf.groups.add(mod);
            conf.groups.add(member);
            logger.warn("config.json NOT FOUND");
            logger.info("trying to create..");
            try {
                byte[] bWrite = Gson.toJson(conf).getBytes();
                OutputStream os = new FileOutputStream(config);
                for (byte b : bWrite) {
                    os.write(b);
                }
                logger.info("Config created.");
                logger.warn("Edit the config and restart application.");
                System.exit(0);
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("Failed to write config.");
                return;
            }

            return;
        }
        try {
            InputStream f = new FileInputStream("./config.json");
            int size = f.available();
            StringBuilder confText = new StringBuilder();
            for (int i = 0; i < size; i++) {
                confText.append((char) f.read());
            }
            conf = Gson.fromJson(confText.toString(), Config.class);
            if (conf.config_version < CONFIG_VERSION) {
                logger.warn("WE ARE USING OUTDATED CONFIG");
                logger.warn("YOU CAN DELETE IT FOR REGENERATION.");
            }
            conf.groups.forEach(a -> {
                if (a.isDefault) {
                    DefaultGroup = a.pGroup;
                }
            });
            logger.info("Config load successfully!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("Unknown Error.");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException.");
        }
    }

    public static Bot getBot(cc.sfclub.polar.events.Message msg) {
        return Wrappers.get(msg.getProvider());
    }

    public static Bot getBot(String Provider) {
        return Wrappers.get(Provider);
    }

    public static void addBot(Bot bot) {
        logger.info("New Provider: ".concat(bot.getPlatfrom()));
        Wrappers.put(bot.getPlatfrom(), bot);
    }
}
