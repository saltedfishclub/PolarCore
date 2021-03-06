package cc.sfclub.core;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;
import lombok.Setter;

public class I18N extends JsonConfig {
    public static final int CONFIG_VERSION = 1;
    /**
     * Set lang
     */
    @Setter
    private static transient I18N inst = new I18N("en_US");
    /**
     * a version number for check outdated config.
     *
     * @return config version
     */
    @Getter
    private final int confVer = CONFIG_VERSION;
    public Server server = new Server();
    public Exceptions exceptions = new Exceptions();
    public Misc misc = new Misc();
    /**
     * @return lang name
     */
    @Getter
    private final transient String locale;

    public I18N(String locale) {
        super("./locale");
        this.locale = locale;
    }

    @Override
    public String getConfigName() {
        return locale + ".json";
    }

    /**
     * get I18N
     *
     * @return instance of i18n
     */
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
        public String STOPPING_SERVER = "Stopping server..";
        public String PLUGIN_PRELOADING = "Scanned Plugin {} ver {}";
        public String PLUGIN_LOADING = "Loading Plugin {} ver {}";
    }

    public class Misc {
        public String UNKNOWN_OPERATOR = "Unknown Command.";
        public String COULD_NOT_LOAD_PLUGIN = "Could not load plugin: {}";
        public String LOADED_PLUGINS = "Loaded plugins({}): {}";
    }

    public class Exceptions {
        public String TABLE_LOADING = "Loading Table \"{}\"..";
        public String CONFIG_OUTDATED = "CONFIG \"{}\" IS OUTDATED! TRY TO DELETE IT FOR A REGENERATION.";
        public String PLUGIN_NOT_SUBSCRIBER = "Plugin {} didn't subscribe any event!";
        public String PLUGIN_DEPENDENCY_MISSING = "Can't find dependency {} for {}!";
        public String PLUGIN_MAIN_CLASS_NOT_FOUND = "Can't find class {} as main class for plugin {}.";
        public String PLUGIN_DATA_CLASS_NOT_FOUND = "Can't find class {} as data class for plugin {}.";
        public String PLUGIN_HAVE_NO_AVAILABLE_CONSTRUCTOR = "Plugin {} have no empty args or accessible constructor.";
        public String PLUGIN_DEPEND_LOOP = "Dependency loop detected! {} and {} will not loaded.";
        public String PLUGIN_FAILED_TO_LOAD = "Failed to load these plugins: {}";
    }
}
