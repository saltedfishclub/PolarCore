package cc.sfclub.core;

import cc.sfclub.command.Source;
import cc.sfclub.core.security.PolarSec;
import cc.sfclub.transform.Bot;
import cc.sfclub.user.Group;
import cc.sfclub.user.User;
import cc.sfclub.user.UserManager;
import cc.sfclub.user.perm.Perm;
import com.dieselpoint.norm.Database;
import com.mojang.brigadier.CommandDispatcher;
import lombok.Getter;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private User CONSOLE;
    private final CommandDispatcher<Source> dispatcher = new CommandDispatcher<>();
    private Map<String, Bot> bots = new HashMap<>();
    public static final String CORE_VERSION = "V4.8.0";
    private Database ORM;

    public Core(CoreCfg config, PermCfg permCfg, DatabaseCfg dbcfg) {
        this.config = config;
        this.permCfg = permCfg;
        this.dbcfg = dbcfg;
        loadDatabase();
        loadUserManager();
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

    private void loadUserManager() {
        userManager = new UserManager(ORM());
        if (!userManager.existsName("CONSOLE")) {
            User console = userManager.register(null, Perm.of(".*"));
            console.setUserName("CONSOLE");
            userManager.addRaw(console);
        }
        this.CONSOLE = userManager.byName("CONSOLE");
        permCfg.getGroupList().forEach(userManager::addRaw);
    }

    /**
     * Get core
     *
     * @return Core
     */
    public static Core get() {
        return core;
    }

    protected static void setDefaultCore(Core core) {
        Core.core = core;
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
        if (ORM == null) {
            logger.error("DATABASE IS MISSING");
        }
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
     * get console user
     *
     * @return console
     */
    public User console() {
        return this.CONSOLE;
    }

    /**
     * @return command dispatcher
     */
    public CommandDispatcher<Source> dispatcher() {
        return this.dispatcher;
    }

    public UserManager userManager() {
        return this.userManager;
    }
}
