package cc.sfclub.core.modules;

import cc.sfclub.util.common.JsonConfig;

public class I18N extends JsonConfig {
    private static final transient I18N inst = new I18N();
    public Server server = new Server();
    public Exceptions exceptions = new Exceptions();

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
        public String FIRST_START = "Go to edit your config first :D";
    }

    public class Exceptions {
        public String GROUP_EXTENDS_NULL = "Group {} extends a null!";
        public String TABLE_NOT_FOUND = "Table of \"{}\" Not Found!! Auto-creating..";
    }
}
