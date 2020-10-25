package cc.sfclub.plugin.java;

import cc.sfclub.core.I18N;
import cc.sfclub.plugin.Plugin;
import cc.sfclub.plugin.PluginDescription;
import cc.sfclub.plugin.PluginLoader;
import cc.sfclub.plugin.SimpleConfig;
import cc.sfclub.plugin.exception.DependencyMissingException;
import cc.sfclub.plugin.exception.InvalidPluginException;
import cc.sfclub.plugin.exception.PluginNotLoadedException;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JavaPluginLoader implements PluginLoader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Set<PolarClassloader> classLoaders = new LinkedHashSet<>();
    private final Path rootPath;
    private final Set<String> failedToLoads = new HashSet<>();
    private final Gson gson = new Gson();
    private final Map<String, Plugin> pluginMap = new HashMap<>();

    public JavaPluginLoader(Path rootPath) {
        this.rootPath = rootPath;
        if (!rootPath.toFile().exists()) {
            rootPath.toFile().mkdir();
        }
    }

    public Class<?> findClass(String clazzName, ClassLoader excepted) throws ClassNotFoundException {
        Class<?>[] result = new Class<?>[]{null};
        classLoaders.stream().filter(cl -> cl != excepted).forEach(cl -> {
            try {
                if (result[0] == null) {
                    result[0] = cl.findClass(clazzName, false);
                }
            } catch (ClassNotFoundException ignored) {
            }
        });
        if (result[0] == null) {
            throw new ClassNotFoundException(clazzName);
        }
        return result[0];
    }

    @Override
    public Plugin getPluginByName(String name) {
        return pluginMap.get(name);
    }

    @Override
    public void loadPlugins() {
        if (!rootPath.toFile().exists()) {
            rootPath.toFile().mkdir();
        }
        Map<String, PluginDescription> preloadingPlugins = new HashMap<>();
        for (File file : Objects.requireNonNull(rootPath.toFile().listFiles())) {
            PluginDescription description = getDescriptionOf(file);
            if (description == null) {
                continue;
            }
            preloadingPlugins.put(description.getName(), description);
            logger.info(I18N.get().server.PLUGIN_PRELOADING, description.getName(), description.getVersion());
        }
        Set<String> errorPlugins = checkDependencies(preloadingPlugins);
        errorPlugins.forEach(preloadingPlugins::remove);
        //Load Plugins
        preloadingPlugins.forEach(this::loadPluginWithDependency);
        logger.error(I18N.get().exceptions.PLUGIN_FAILED_TO_LOAD, Arrays.toString(failedToLoads.toArray()));
    }

    private boolean loadPluginWithDependency(String name, PluginDescription plugin) {
        return loadPluginWithDependency(name, plugin, new Stack<>());
    }

    @SneakyThrows
    private boolean loadPluginWithDependency(String name, PluginDescription plugin, Stack<String> loadStack) {
        if (failedToLoads.contains(name)) {
            return false;
        }
        if (loadStack.search(name) != -1) {
            logger.error(I18N.get().exceptions.PLUGIN_DEPEND_LOOP, name, loadStack.pop());
            logger.error("Plugin Dependency Stack: {}", Arrays.toString(loadStack.toArray()));
            return false;
        }
        loadStack.push(name);
        if (isPluginLoaded(name)) {
            return false;
        }
        for (String dep : plugin.getDependencies()) {
            if (!isPluginLoaded(dep)) {
                if (!loadPluginWithDependency(dep, plugin, loadStack)) {
                    return false;
                }
            }
        }
        try {
            loadPlugin(plugin.getPluginFile());
            return true;
        } catch (DependencyMissingException e) {
            logger.error("Unknown Error!", e);
            return false;
        }
    }

    /**
     * @return error plugins
     */
    public Set<String> checkDependencies(Map<String, PluginDescription> preloadingPlugins) {
        Set<String> errorPlugins = new HashSet<>();
        while (preloadingPlugins.values().iterator().hasNext()) {
            PluginDescription desc = preloadingPlugins.values().iterator().next();
            if (desc.getDependencies().isEmpty()) {
                continue;
            }
            Check:
            for (String dependency : desc.getDependencies()) {
                if (!preloadingPlugins.containsKey(dependency)) {
                    logger.warn(I18N.get().exceptions.PLUGIN_DEPENDENCY_MISSING, dependency, desc.getName());
                    //preloadingPlugins.remove(desc.getName());
                    errorPlugins.add(desc.getName());
                    break Check;
                }
            }
        }
        return errorPlugins;
    }

    @Override
    public void unloadPlugins() {

    }

    @Override
    public PluginDescription getDescriptionOf(File file) {
        try {
            JarFile jar = new JarFile(file);
            JarEntry descriptionFile = jar.getJarEntry("plugin.json");
            PluginDescription description = gson.fromJson(new BufferedReader(new InputStreamReader(jar.getInputStream(descriptionFile))), PluginDescription.class);
            description.setPluginFile(file);
            if (description.getMain() != null && description.getName() != null && description.getVersion() != null) {
                return description;
            }
        } catch (IOException e) {
            logger.debug("Failed to get description of {}!", file.getName());
            return null;
        }
        return null;
    }

    public PolarClassloader getClassloaderOf(String name) {
        if (!pluginMap.containsKey(name)) {
            return null;
        }
        return (PolarClassloader) pluginMap.get(name).getClass().getClassLoader();
    }

    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, DependencyMissingException {
        PluginDescription description = getDescriptionOf(file);
        if (description == null) {
            throw new InvalidPluginException("Failed to load " + file.getAbsolutePath());
        }
        if (checkDependencies(Map.of(description.getName(), description)).size() != 0) {
            throw new DependencyMissingException("Dependency missing for " + description.getName());
        }
        try {
            PolarClassloader cl = new PolarClassloader(new URL[]{file.toURI().toURL()}, this, description.getName());
            Class<?> pluginClass = cl.findClass(description.getMain(), false);
            Plugin plugin = (Plugin) pluginClass.getConstructor().newInstance();
            plugin.setDescription(description);
            plugin.setLoaded(true);
            plugin.setDataFolder(new File(rootPath.toString() + "/" + description.getName()));
            if (description.getDataClass() != null) {
                //Setup simpleConfig
                try {
                    Class<?> clazz = cl.findClass(description.getDataClass(), false);
                    plugin.setConfig(new SimpleConfig<>(plugin, clazz));
                } catch (ClassNotFoundException e) {
                    logger.error(I18N.get().exceptions.PLUGIN_DATA_CLASS_NOT_FOUND, description.getDataClass(), description.getName());
                }
            }
            pluginMap.put(description.getName(), plugin);
        } catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            if (e instanceof ClassNotFoundException) {
                logger.error(I18N.get().exceptions.PLUGIN_MAIN_CLASS_NOT_FOUND, description.getMain(), description.getName());
            } else if (e instanceof NoSuchMethodException) {
                logger.error(I18N.get().exceptions.PLUGIN_HAVE_NO_AVAILABLE_CONSTRUCTOR, description.getName());
            }
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            if (e instanceof IllegalAccessException) {
                logger.error(I18N.get().exceptions.PLUGIN_HAVE_NO_AVAILABLE_CONSTRUCTOR, description.getName());
            }
        }
        return null;
    }

    @Override
    public void unloadPlugin(Plugin plugin) throws PluginNotLoadedException {

    }

    @Override
    public boolean isPluginLoaded(String name) {
        return pluginMap.containsKey(name);
    }
}
