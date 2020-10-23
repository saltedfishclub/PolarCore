package cc.sfclub.core;

import cc.sfclub.command.Source;
import cc.sfclub.transform.Bot;
import cc.sfclub.user.Group;
import cc.sfclub.user.User;
import cc.sfclub.user.perm.Perm;
import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import lombok.Getter;
import lombok.NonNull;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Core {
    private static Core core;

    public static final int CONFIG_VERSION = 1;
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
    private final Dao ORM;
    private User CONSOLE;
    private final CommandDispatcher<Source> dispatcher = new CommandDispatcher<>();
    private Map<String, Bot> bots = new HashMap<>();
    public static final String CORE_VERSION = "V4.3.7";

    public Core(CoreCfg config, PermCfg permCfg, DataSource ds) {
        this.config = config;
        this.permCfg = permCfg;
        this.ORM = new NutDao(ds);
        if (!ORM().exists(User.class)) {
            Core.getLogger().warn(I18N.get().exceptions.TABLE_NOT_FOUND, User.class.getName());
            ORM().create(User.class, false);
            User console = new User(null, new Perm(".*"));
            console.setUserName("CONSOLE");
            ORM().insert(console);
        }
        this.CONSOLE = User.byName("CONSOLE");
        if (!Core.get().ORM().exists(Group.class)) {
            Core.getLogger().warn(I18N.get().exceptions.TABLE_NOT_FOUND, Group.class.getName());
            Core.get().ORM().create(Group.class, false);
            permCfg.getGroupList().forEach(Core.get().ORM()::insert);
        }
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
    public Dao ORM() {
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
