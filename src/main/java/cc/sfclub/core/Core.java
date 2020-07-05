package cc.sfclub.core;

import cc.sfclub.command.Source;
import cc.sfclub.transform.Bot;
import cc.sfclub.user.User;
import com.google.gson.Gson;
import com.mojang.brigadier.CommandDispatcher;
import lombok.Getter;
import lombok.NonNull;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
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
    private final User CONSOLE;
    private final CommandDispatcher<Source> dispatcher = new CommandDispatcher<>();
    private Map<String, Bot> bots = new HashMap<>();
    public static final String CORE_VERSION = "V4.1.2";
    @Getter
    private static final PluginManager pluginManager = new DefaultPluginManager(new File("./plugins").toPath());

    public Core(CoreCfg config, PermCfg permCfg, DataSource ds) {
        core = this;
        this.config = config;
        this.permCfg = permCfg;
        this.ORM = new NutDao(ds);
        this.CONSOLE = ORM.fetch(User.class, Cnd.where("userName", "=", "CONSOLE"));
    }

    /**
     * Get core
     *
     * @return Core
     */
    public static Core get() {
        return core;
    }

    public void registerBot(@NonNull Bot bot) {
        bots.put(bot.getName(), bot);
    }

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
