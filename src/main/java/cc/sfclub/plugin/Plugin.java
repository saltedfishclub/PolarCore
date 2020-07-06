package cc.sfclub.plugin;

import lombok.Data;

import java.io.File;

@Data
public abstract class Plugin {
    private Description description;
    private SimpleConfig simpleConfig;
    private File dataFolder;

    public void saveDefaultConfig() {
        simpleConfig = (SimpleConfig) simpleConfig.saveDefaultOrLoad();
    }
}
