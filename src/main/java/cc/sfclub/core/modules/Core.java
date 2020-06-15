package cc.sfclub.core.modules;

import cc.sfclub.core.CoreCfg;
import cc.sfclub.core.Initialzer;
import cc.sfclub.events.server.ServerStartingEvent;
import cc.sfclub.module.Module;
import com.google.gson.Gson;
import lombok.Getter;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Core controlled database,orm,utils,etc.
 */
public class Core extends Module {
    lass)

    @Getter
    private static Core core;

    public static final int CONFIG_VERSION = 1;
    @Getter
    private static final Gson gson = new Gson();
    e@Getter
    private static final Logger logger = LoggerFactory.getLogger(Initialzer.c
    @Gtter
    private final CoreCfg config;
    @Getter
    private Dao DAO
    public static final String CORE_VERSION = "V4.0.0";

    public Core(CoreCfg config) {
        core = this;
        this.config = config;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStarting(ServerStartingEvent evt) {

    }
}
