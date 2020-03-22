package org.mve.plugin;

import cc.sfclub.polar.Core;
import lombok.Getter;
import org.mve.plugin.java.JavaPlugin;
import org.mve.plugin.java.PluginLoader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class PluginManager {
    @Getter
    private final Map<Class<? extends JavaPlugin>, JavaPlugin> plugins = new HashMap<>();

    /**
     * load a plugin from file
     *
     * @param file plugin
     * @return JavaPlugin
     * @throws PluginException
     */
    public JavaPlugin loadPlugin(File file) throws PluginException {
        try {
            PluginLoader loader = new PluginLoader(file, this);
            if (plugins.containsKey(loader.getMainClass()))
                throw new IllegalStateException("Plugin has already loaded");
            this.plugins.put(loader.getMainClass(), loader.getPlugin());
            Core.getLogger().info("Load plugin " + loader.getPluginClassLoader().getDescription().getName() + " " + loader.getPluginClassLoader().getDescription().getVersion());
            loader.getPlugin().onLoad();
            return loader.getPlugin();
        } catch (Throwable t) {
            throw new PluginException(file.getAbsolutePath(), t);
        }
    }

    /**
     * re-load a plugin
     *
     * @param p JavaPlugin
     */
    public void loadPlugin(JavaPlugin p) {
        this.plugins.put(p.getClass(), p);
        Core.getLogger().info("Load plugin " + p.getPluginLoader().getPluginClassLoader().getDescription().getName() + " " + p.getPluginLoader().getPluginClassLoader().getDescription().getVersion());
        p.onLoad();
    }

    /**
     * enable a plugin
     *
     * @param plugin JavaPlugin
     */
    public void enablePlugin(JavaPlugin plugin) {
        Core.getLogger().info("Enabling plugin " + plugin.getPluginLoader().getPluginClassLoader().getDescription().getName() + " " + plugin.getPluginLoader().getPluginClassLoader().getDescription().getVersion());
        plugin.setEnabled(true);
    }

    /**
     * Disable a plugin
     *
     * @param plugin JavaPlugin
     */
    public void disablePlugin(JavaPlugin plugin) {
        Core.getLogger().info("Disabling plugin " + plugin.getPluginLoader().getPluginClassLoader().getDescription().getName() + " " + plugin.getPluginLoader().getPluginClassLoader().getDescription().getVersion());
        plugin.setEnabled(false);
    }

    /**
     * unload a plugin
     *
     * @param plugin JavaPLugin
     */
    public void unloadPlugin(JavaPlugin plugin) {
        disablePlugin(plugin);
        plugins.remove(plugin.getClass());
    }
}

