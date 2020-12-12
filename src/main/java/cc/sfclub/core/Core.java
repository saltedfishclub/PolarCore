package cc.sfclub.core;

import cc.sfclub.command.Source;
import cc.sfclub.core.security.PolarSec;
import cc.sfclub.events.Event;
import cc.sfclub.events.internal.MessageListener;
import cc.sfclub.events.server.ServerStartedEvent;
import cc.sfclub.events.server.ServerStoppingEvent;
import cc.sfclub.plugin.PluginManager;
import cc.sfclub.transform.Bot;
import cc.sfclub.user.Group;
import cc.sfclub.user.User;
import cc.sfclub.user.UserManager;
import com.dieselpoint.norm.Database;
import com.mojang.brigadier.CommandDispatcher;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 核心。
 */
public class Core {
    private static Core core;

    public static final int CONFIG_VERSION = 2;
    /**
     * get a logger
     *
     * @return logger
     */
    private final Logger logger = LoggerFactory.getLogger("Core");
    private final CoreCfg config;
    private final PermCfg permCfg;
    private final DatabaseCfg dbcfg;
    private UserManager userManager;
    @Getter
    private final PolarSec polarSec = new PolarSec();
    private final CommandDispatcher<Source> dispatcher = new CommandDispatcher<>();
    private final Map<String, Bot> bots = new HashMap<>();
    private final PluginManager pluginManager = new PluginManager("./plugins");
    public static final String CORE_VERSION = "V4.8.0";
    private Database ORM;

    public Core(CoreCfg config, PermCfg permCfg, DatabaseCfg dbcfg) {
        Core.core = this;
        this.config = config;
        this.permCfg = permCfg;
        this.dbcfg = dbcfg;
        loadLang(config);
        loadDatabase();
        loadUserManager();
        pluginManager.loadPlugins();
        Event.registerListeners(new MessageListener());
        Event.postEvent(new ServerStartedEvent());
    }

    private void loadDatabase() {
        ORM = new Database();
        ORM.setJdbcUrl(dbcfg.getJdbcUrl());
        ORM.setUser(dbcfg.getUser());
        ORM.setPassword(dbcfg.getPassword());
        //load user table
        logger.info("Loading Database..");
        if (config.isResetDatabase()) {
            ORM().createTable(User.class);
            logger.info(I18N.get().exceptions.TABLE_LOADING, "user");
            ORM().createTable(Group.class);
            logger.info(I18N.get().exceptions.TABLE_LOADING, "userGroup");
            config.setResetDatabase(false);
            config.saveConfig();
        }
    }

    private void loadLang(CoreCfg cfg) {
        new File("locale").mkdir();
        I18N i18N = new I18N(cfg.getLocale());
        I18N.setInst((I18N) i18N.saveDefaultOrLoad());
        if (i18N.getConfVer() != I18N.CONFIG_VERSION) {
            logger.warn(I18N.get().exceptions.CONFIG_OUTDATED, cfg.getConfigName());
        }
    }

    private void loadUserManager() {
        userManager = new UserManager(ORM());
        permCfg.getGroupList().forEach(userManager::addRaw);
    }

    public void stop() {
        logger.info(I18N.get().server.STOPPING_SERVER);
        Event.postEvent(new ServerStoppingEvent());
        pluginManager.unloadPlugins();
        Event.unregisterAllListeners();
    }

    /**
     * Get core
     *
     * @return Core
     */
    public static Core get() {
        return core;
    }

    /**
     * Register a bot
     *
     * @param bot bot
     */

    public void registerBot(@NonNull Bot bot) {
        bots.put(bot.getName(), bot);
    }

    /**
     * get a bot
     *
     * @param name bot name
     * @return nullable bot
     */
    public Optional<Bot> bot(@NonNull String name) {
        return Optional.ofNullable(bots.get(name));
    }

    /**
     * Get ORM
     *
     * @return ORM
     */
    public Database ORM() {
        assert ORM != null;
        return ORM;
    }

    /**
     * get config about permission groups
     *
     * @return PermCfg
     */
    public PermCfg permCfg() {
        return permCfg;
    }

    /**
     * get config
     *
     * @return config
     */
    public CoreCfg config() {
        return this.config;
    }


    /**
     * @return command dispatcher
     */
    public CommandDispatcher<Source> dispatcher() {
        return this.dispatcher;
    }

    /**
     * 获取用户管理器
     *
     * @return
     */
    public UserManager userManager() {
        return this.userManager;
    }
}
