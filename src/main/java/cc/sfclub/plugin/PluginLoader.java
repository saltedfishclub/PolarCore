package cc.sfclub.plugin;

import cc.sfclub.plugin.exception.DependencyMissingException;
import cc.sfclub.plugin.exception.InvalidPluginException;
import cc.sfclub.plugin.exception.PluginNotLoadedException;

import java.io.File;

public interface PluginLoader {
    void loadPlugins();

    void unloadPlugins();

    Plugin getPluginByName(String name);

    PluginDescription getDescriptionOf(File file);

    Plugin loadPlugin(File file) throws InvalidPluginException, DependencyMissingException;

    void unloadPlugin(Plugin plugin) throws PluginNotLoadedException;

    boolean isPluginLoaded(String name);
}
