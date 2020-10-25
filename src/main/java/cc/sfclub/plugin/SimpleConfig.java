package cc.sfclub.plugin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleConfig<C> {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String root;
    private C configObj;
    @Setter
    @Getter
    private String configFileName;
    private Class<C> clazz;

    @SneakyThrows
    public SimpleConfig(Plugin plugin, Class<C> configClass) {
        plugin.getDataFolder().mkdirs();
        this.root = plugin.getDataFolder().getAbsolutePath();
        this.clazz = configClass;
        this.configObj = configClass.getDeclaredConstructor().newInstance();
        saveDefault();
        reloadConfig();
    }

    /**
     * Save Config to config.json
     */
    @SneakyThrows
    public void saveConfig() {
        Files.write(new File(root + "/" + getConfigFileName()).toPath(), gson.toJson(configObj).getBytes());
    }

    @SneakyThrows
    public void saveDefault() {
        Path a = new File(root + "/" + getConfigFileName()).toPath();
        if (!Files.exists(a)) {
            Files.write(a, gson.toJson(configObj).getBytes());
        }
    }

    public C get() {
        return configObj;
    }

    public void set(C c) {
        this.configObj = c;
    }

    /**
     * Reload Config
     */
    @SneakyThrows
    public void reloadConfig() {
        configObj = gson.fromJson(new BufferedReader(new FileReader(root + "/" + getConfigFileName())), clazz);
    }
}