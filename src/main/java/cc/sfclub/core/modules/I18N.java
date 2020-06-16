package cc.sfclub.core.modules;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;
import lombok.Setter;

public class I18N extends JsonConfig {
    public static final int CONFIG_VERSION = 1;
    @Setter
    private static transient I18N inst = new I18N("en_US");
    @Getter
    private final int confVer = CONFIG_VERSION;
    public Server server = new Server();
    public Exceptions exceptions = new Exceptions();
    @Getter
    private final transient String locale;

    public I18N(String locale) {
        super("./locale");
        this.locale = locale;
        inst = this;
    }

    @Override
    public String getConfigName() {
        return locale + ".json";
    }

    public static I18N get() {
        return inst;
    }

    public class Server {
        public String STARTING = "PolarCore {} starting..";
        public String STARTED = "Server Started.";
        public String LOAD_MODULE = "Loading {} ver {}";
        public String LOADING_MODULES = "Loading Modules..";
        public String LOADED_MODULE = "Modules Loaded.";
        public String FIRST_START = "Go to edit your config first :D";
    }

    public class Exceptions {
        public String TABLE_NOT_FOUND = "Table of \"{}\" Not Found!! Auto-creating..";
        public String CONFIG_OUTDATED = "CONFIG \"{}\" IS OUTDATED! TRY TO DELETE IT FOR A REGENERATION.";
    }
}
