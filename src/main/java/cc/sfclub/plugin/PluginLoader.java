package cc.sfclub.plugin;

import cc.sfclub.plugin.exception.DependencyMissingException;
import cc.sfclub.plugin.exception.InvalidPluginException;

import java.io.File;

public interface PluginLoader {
    String getFilePattern();

    PluginDescription getDescriptionOf(File file);

    Plugin loadPlugin(File file) throws InvalidPluginException, DependencyMissingException;
}
