package cc.sfclub.polar;

import cc.sfclub.polar.events.Message;
import cc.sfclub.polar.events.messages.TextMessage;
import cc.sfclub.polar.modules.security.PolarSec;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Core {
    /**
     * @return Core
     */
    @Getter
    private static final Core instance = new Core();
    /**
     * @return latest config version
     */
    private static final int CONFIG_VERSION = 7;
    /**
     * @return logger
     */
    @Getter
    private static final Logger logger = LoggerFactory.getLogger(Core.class);
    /**
     * @return config
     */
    @Getter
    private static Config conf;
    /**
     * @return GSon
     */
    @Getter
    private static Gson Gson = new Gson();
    /**
     * @return Message EventBus
     */
    @Getter
    private EventBus message = EventBus.getDefault();
    private DruidDataSource dataSource = new DruidDataSource();
    /**
     * @return PluginManager
     */
    @Getter
    private PluginManager pluginManager = new PluginManager();
    /**
     * @return DAO
     */
    @Getter
    private Dao dao;
    /**
     * @return Wrappers Map(ProviderName->Bot(Wrapper))
     */
    @Getter
    private HashMap<String, Bot> wrappers = new HashMap<>();
    /**
     * @return CommandManager
     */
    @Getter
    private CommandManager commandManager = new CommandManager();
    /**
     * @return plugins
     */
    @Getter
    private ArrayList<JavaPlugin> plugins = new ArrayList<>();
    private boolean loaded = false;
    /**
     * callback when core-loading finished
     */
    @Setter
    private LoadCallback cb;
    /**
     * PolarSec
     */
    @Getter
    private PolarSec pSec;

    private Core() {
    }

    public static void main(String[] args) {
        instance.init();
        instance.waitCommand();
        System.exit(0);
    }

    /**
     * init.
     */
    public void init() {
        logger.info("Loading Config");
        loadConfig();
        if (!loaded) {
            logger.info("Loading Databases");
            loadDatabase();
            loaded = true;
        }
        logger.info("Loading User and Perm");
        logger.info("Loading Events");
        loadEventBus();
        logger.info("Loading CommandManager");
        commandManager.getCommandMap().clear();
        wrappers.clear();
        logger.info("Loading Plugins");
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
    }

    private void loadPlugins() {
        plugins.forEach(pluginManager::disablePlugin);
        pluginManager.getPlugins().clear();
        plugins.clear();

        if (conf.usePolarSecurity) {
            pSec = new PolarSec();
            pSec.onEnable();
        }
        File a = new File("./plugins");
        if (!a.exists()) {
            if (!a.mkdir()) {
                getLogger().error("Could not create plugins folder.");
            }
            new File("./plugins/config").mkdir();
            return;
        }
        for (File file : a.listFiles()) {
            if (!file.isDirectory()) {
                try {
                    JavaPlugin jp = pluginManager.loadPlugin(file);
                    if (jp != null) {
                        pluginManager.enablePlugin(jp);
                        plugins.add(jp);
                    }
                } catch (PluginException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadDatabase() {
        dataSource.setUrl("jdbc:mysql://" + conf.database.host + "/" + conf.database.database);
        dataSource.setUsername(conf.database.user);
        dataSource.setPassword(conf.database.password);
        dao = new NutDao(dataSource);
        if (!dao.exists("user")) {
            logger.info("Creating Table: user");
            dao.create(User.class, false);
            User a = new User(0L, "CLI", "OPERATOR");
            a.userName = "CONSOLE";
            UserUtil.addUser(a);
        }
        logger.info("{} User loaded!", dao.count("user"));
        if (!dao.exists("perm")) {
            dao.create(Group.class, false);
            conf.groups.forEach(PermUtil::addGroup);
        }
        logger.info("{} Groups loaded", dao.count("perm"));
    }

    private void loadEventBus() {
        if (message.isRegistered(commandManager)) {
            message.unregister(commandManager);
        }
        message.register(commandManager);
    }

    private void waitCommand() {
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        String command;
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if ("stop".equals(command)) {
                logger.info("Stopping Server...");
                plugins.forEach(pluginManager::disablePlugin);
                if (conf.usePolarSecurity) {
                    pSec.onDisable();
                }
                break;
            }
            message.post(new TextMessage("CLI", i, 0L, command, 0));
            i++;
        }
        scanner.close();
    }

    private void loadConfig() {
        File config = new File("config.json");
        if (!config.exists()) {
            conf = new Config();
            conf.config_version = CONFIG_VERSION;
            //
            Group op = new Group();
            op.pGroup = "OPERATOR";
            op.nodes.add(".*");
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
                BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(config));
                os.write(bWrite);
                os.flush();
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
            BufferedInputStream f = new BufferedInputStream(new FileInputStream("./config.json"));
            int size = f.available();
            StringBuilder confText = new StringBuilder();
            for (int i = 0; i < size; i++) {
                confText.append((char) f.read());
            }
            conf = Gson.fromJson(confText.toString(), Config.class);
            if (conf.config_version < CONFIG_VERSION) {
                logger.warn("YOU ARE USING OUTDATED CONFIG");
                logger.warn("DELETE IT FOR REGENERATION.");
            }
            conf.groups.forEach(a -> {
                if (a.isDefault) {
                    conf.defaultGroup = a.pGroup;
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

    /**
     * get a bot by a message
     *
     * @param msg Message
     * @return Bot
     */
    public Bot getBot(Message msg) {
        return wrappers.get(msg.getProvider());
    }

    /**
     * get a bot by provider name
     *
     * @param provider provider name
     * @return Bot
     */
    public Bot getBot(String provider) {
        return wrappers.get(provider);
    }

    /**
     * add a bot
     *
     * @param bot bot
     */
    public void addBot(Bot bot) {
        logger.info("New Provider: ".concat(bot.getPlatfrom()));
        wrappers.put(bot.getPlatfrom(), bot);
    }
}
