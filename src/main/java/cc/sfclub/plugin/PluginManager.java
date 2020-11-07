package cc.sfclub.plugin;

import cc.sfclub.core.I18N;
import cc.sfclub.plugin.exception.DependencyMissingException;
import cc.sfclub.plugin.exception.InvalidPluginException;
import cc.sfclub.plugin.java.JavaPluginLoader;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class PluginManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<PluginLoader> pluginLoaders = new LinkedList<>();
    private final Set<String> failedToLoads = new HashSet<>();
    private final Map<String, Plugin> pluginMap = new HashMap<>();
    private final String rootPath;

    @SneakyThrows
    public PluginManager(String rootPath) {
        this.rootPath = rootPath;
        pluginLoaders.add(new JavaPluginLoader(new File(rootPath).toPath(), this));
    }
    public void loadPlugins() {
        File pluginsDir = new File(rootPath);
        Map<String, PluginDescription> preloadingPlugins = new HashMap<>();
        //Pre-load descriptions
        for (File file : pluginsDir.listFiles()) {
            PluginLoader loader = fileToLoader(file);
            if (loader == null) {
                continue;
            }
            PluginDescription desc = loader.getDescriptionOf(file);
            if (desc == null) {
                continue;
            }
            preloadingPlugins.put(desc.getName(), desc);
            logger.info(I18N.get().server.PLUGIN_PRELOADING, desc.getName(), desc.getVersion());
        }
        //End
        //Check dependencies
        Set<String> errorPlugins = checkDependencies(preloadingPlugins);
        errorPlugins.forEach(preloadingPlugins::remove);
        preloadingPlugins.forEach(this::loadPluginWithDependency);
        if (failedToLoads.size() != 0) {
            logger.error(I18N.get().exceptions.PLUGIN_FAILED_TO_LOAD, Arrays.toString(failedToLoads.toArray()));
        }
    }

    public Set<String> checkDependencies(Map<String, PluginDescription> preloadingPlugins) {
        Set<String> errorPlugins = new HashSet<>();
        Iterator<PluginDescription> iter = preloadingPlugins.values().iterator();
        while (iter.hasNext()) {
            PluginDescription desc = iter.next();
            if (desc.getDependencies() == null) {
                continue;
            }

            for (String dependency : desc.getDependencies()) {
                if (!preloadingPlugins.containsKey(dependency)) {
                    logger.warn(I18N.get().exceptions.PLUGIN_DEPENDENCY_MISSING, dependency, desc.getName());
                    //preloadingPlugins.remove(desc.getName());
                    errorPlugins.add(desc.getName());
                    break;
                }
            }
        }
        return errorPlugins;
    }

    private PluginLoader fileToLoader(File file) {
        for (PluginLoader pluginLoader : pluginLoaders) {
            if (file.getName().matches(pluginLoader.getFilePattern())) {
                return pluginLoader;
            }
        }
        return null;
    }

    private boolean loadPluginWithDependency(String name, PluginDescription plugin) {
        return loadPluginWithDependency(name, plugin, new Stack<>());
    }

    public Plugin loadPlugin(File file) throws InvalidPluginException, DependencyMissingException {
        PluginLoader loader = fileToLoader(file);
        if (loader == null) {
            return null;
        }
        return loader.loadPlugin(file);
    }

    public Collection<Plugin> getPlugins() {
        return pluginMap.values();
    }

    /**
     * 插件是否加载
     *
     * @param name
     * @return
     */
    public boolean isPluginLoaded(String name) {
        return pluginMap.get(name) != null && pluginMap.get(name).isLoaded();
    }

    public Plugin getPlugin(String name) {
        return pluginMap.get(name);
    }

    @SneakyThrows
    private boolean loadPluginWithDependency(String name, PluginDescription plugin, Stack<String> loadStack) {
        if (failedToLoads.contains(name)) {
            return false;
        }
        if (loadStack.search(name) != -1) {
            failedToLoads.add(name);
            logger.error(I18N.get().exceptions.PLUGIN_DEPEND_LOOP, name, loadStack.pop());
            logger.error("Plugin Dependency Stack: {}", Arrays.toString(loadStack.toArray()));
            return false;
        }
        loadStack.push(name);
        if (isPluginLoaded(name)) {
            return false;
        }
        if (plugin.getDependencies() != null) {
            for (String dep : plugin.getDependencies()) {
                if (!isPluginLoaded(dep)) {
                    if (!loadPluginWithDependency(dep, plugin, loadStack)) {
                        failedToLoads.add(dep);
                        return false;
                    }
                }
            }
        }
        if (plugin.getSoftDependencies() != null) {
            for (String dep : plugin.getSoftDependencies()) {
                if (!isPluginLoaded(dep)) {
                    if (!loadPluginWithDependency(dep, plugin, loadStack)) {
                        failedToLoads.add(dep);
                        return true;
                    }
                }
            }
        }
        try {
            Plugin p = loadPlugin(plugin.getPluginFile());
            if (p == null) {
                return false;
            }
            p.onEnable();
            pluginMap.put(plugin.getName(), p);
            return true;
        } catch (DependencyMissingException e) {
            logger.error("Unknown Error!", e);
            return false;
        }
    }

    public boolean unloadPlugin(String name) {
        if (!isPluginLoaded(name)) {
            return false;
        }
        Plugin plugin = getPlugin(name);
        plugin.setLoaded(false);
        plugin.onDisable();
        if (plugin.getConfig() != null) plugin.getConfig().saveConfig();
        pluginMap.put(name, null);
        return true;
    }

    public void unloadPlugins() {
        pluginMap.keySet().forEach(this::unloadPlugin);
        pluginMap.clear();
    }
}
