package cc.sfclub.core.modules;

import cc.sfclub.core.CoreCfg;
import cc.sfclub.core.PermCfg;
import cc.sfclub.events.server.ServerStartingEvent;
import cc.sfclub.module.Description;
import cc.sfclub.module.Module;
import cc.sfclub.user.User;
import com.google.gson.Gson;
import lombok.Getter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class Core extends Module {
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
    public static final String CORE_VERSION = "V4.0.0";

    public Core(CoreCfg config, PermCfg permCfg, DataSource ds) {
        this.description = new Description("Core", CORE_VERSION, this.getClass().getName());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStarting(ServerStartingEvent evt) {

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
}
