package cc.sfclub.core;

import cc.sfclub.command.Source;
import cc.sfclub.transform.Bot;
import cc.sfclub.user.Group;
import cc.sfclub.user.User;
import cc.sfclub.user.perm.Perm;
import com.dieselpoint.norm.Database;
import com.google.gson.Gson;
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
     * Get a public GSON
     *
     * @return GSOn
     */
    @Getter
    private static final Gson gson = new Gson();
    /**
     * get a logger
     *
     * @return logger
     */
    @Getter
    private static final Logger logger = LoggerFactory.getLogger("Core");
    private final CoreCfg config;
    private final PermCfg permCfg;
    private final DatabaseCfg dbcfg;
    private User CONSOLE;
    private final CommandDispatcher<Source> dispatcher = new CommandDispatcher<>();
    private Map<String, Bot> bots = new HashMap<>();
    public static final String CORE_VERSION = "V4.3.7";
    private Database ORM;

    public Core(CoreCfg config, PermCfg permCfg, DatabaseCfg dbcfg) {
        this.config = config;
        this.permCfg = permCfg;
        this.dbcfg = dbcfg;
    }

    public void loadDatabase() {
        ORM = new Database();
        ORM.setJdbcUrl(dbcfg.getJdbcUrl());
        ORM.setUser(dbcfg.getUser());
        ORM.setPassword(dbcfg.getPassword());
        //load user table
        Core.getLogger().info("Loading Database..");
        if (config.isResetDatabase()) {
            ORM().createTable(User.class);
            Core.getLogger().info(I18N.get().exceptions.TABLE_LOADING, "user");
            if (ORM().table("userGroup") == null) {
                ORM().createTable(Group.class);
            }
            Core.getLogger().info(I18N.get().exceptions.TABLE_LOADING, Group.class.getName());
            config.setResetDatabase(false);
            config.saveConfig();
        }
        if (!User.existsName("CONSOLE")) {
            User console = new User(null, new Perm(".*"));
            console.setUserName("CONSOLE");
            //todo Caused by: org.sqlite.SQLiteException: [SQLITE_ERROR] SQL error or missing database (table User has no column named permList)
            ORM().insert(console);
        }
        this.CONSOLE = User.byName("CONSOLE");
        permCfg.getGroupList().forEach(Core.get().ORM()::insert);
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
            getLogger().error("DATABASE IS MISSING");
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
}
