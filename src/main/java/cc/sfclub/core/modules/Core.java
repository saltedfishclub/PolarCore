package cc.sfclub.core.modules;

import cc.sfclub.core.CoreCfg;
import cc.sfclub.core.PermCfg;
import cc.sfclub.events.server.ServerStartingEvent;
import cc.sfclub.module.Module;
import com.google.gson.Gson;
import lombok.Getter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

public class Core extends Module {
    private static Core core;

    public static final int CONFIG_VERSION = 1;
    @Getter
    private static final Gson gson = new Gson();
    @Getter
    private static final Logger logger = LoggerFactory.getLogger("Core");
    private final CoreCfg config;
    private final PermCfg permCfg;
    private final Dao ORM;
    public static final String CORE_VERSION = "V4.0.0";

    public Core(CoreCfg config, PermCfg permCfg, DataSource ds) {
        core = this;
        this.config = config;
        this.permCfg = permCfg;
        this.ORM = new NutDao(ds);
        EventBus.getDefault().register(this);
    }

    public static Core get() {
        return core;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStarting(ServerStartingEvent evt) {

    }

    public Dao ORM() {
        return ORM;
    }

    public PermCfg permCfg() {
        return permCfg;
    }

    public CoreCfg config() {
        return this.config;
    }
}
