package cc.sfclub.core.modules;

import cc.sfclub.module.Config;

public class I18N extends Config {
    private static final transient I18N inst = new I18N();
    public Server server = new Server();

    private I18N() {
        super(".");
    }

    public static I18N get() {
        return inst;
    }

    public class Server {
        public String STARTING = "PolarCore {} starting..";
        public String STARTED = "Server Started.";
        public String LOAD_MODULE = "Loading {} ver {}";
        public String LOADING_MODULES = "Loading Modules..";
        public String LOADED_MODULE = "Plugin Loaded.";
    }
}
