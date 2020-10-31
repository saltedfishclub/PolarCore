package cc.sfclub.plugin;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class PluginDescription {
    /**
     * Plugin Name.
     */
    private String name;
    /**
     * Plugin Main Class.
     */
    private String main;
    /**
     * Plugin Version
     */
    private String version;
    /**
     * Auto Register main class as a listener.
     */
    private boolean autoRegister;
    /**
     * Config class
     */
    @SerializedName("dataClass")
    private String dataClass;
    /**
     * dependencies.
     */
    @SerializedName("depend")
    private Set<String> dependencies;
    /**
     * dependencies but optional.
     */
    @SerializedName("soft-depend")
    private Set<String> softDependencies;
    @Setter
    private transient File pluginFile;

    public Set<String> getAllDependencies() {
        Set<String> d = new HashSet<>();
        d.addAll(softDependencies);
        d.addAll(dependencies);
        return d;
    }
}
