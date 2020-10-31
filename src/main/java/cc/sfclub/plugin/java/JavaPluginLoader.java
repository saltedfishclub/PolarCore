package cc.sfclub.plugin.java;

import cc.sfclub.core.I18N;
import cc.sfclub.events.Event;
import cc.sfclub.plugin.*;
import cc.sfclub.plugin.exception.DependencyMissingException;
import cc.sfclub.plugin.exception.InvalidPluginException;
import com.google.gson.Gson;
import lombok.Getter;
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
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JavaPluginLoader implements PluginLoader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Path rootPath;
    private final Gson gson = new Gson();
    @Getter
    private final PluginManager pluginManager;

    public JavaPluginLoader(Path rootPath, PluginManager pm) {
        this.rootPath = rootPath;
        this.pluginManager = pm;
        if (!rootPath.toFile().exists()) {
            rootPath.toFile().mkdir();
        }
    }

    /**
     * 在所有插件Classpath中寻找class
     *
     * @param clazzName 类名
     * @param excepted  排除掉的CL
     * @return clazz
     * @throws ClassNotFoundException
     */
    public Class<?> findClass(String clazzName, ClassLoader excepted) throws ClassNotFoundException {
        Class<?>[] result = new Class<?>[]{null};
        pluginManager.getPlugins().stream().filter(cl -> cl.getClass().getClassLoader() != excepted).forEach(p -> {
            PolarClassloader cl = (PolarClassloader) p.getClass().getClassLoader();
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
    public String getFilePattern() {
        return ".*\\.jar";
    }


    /**
     * 获取插件文件描述信息
     *
     * @param file
     * @return
     */
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

    /**
     * 获取插件PolarClassloader.Nullable
     *
     * @param name
     * @return
     */
    public PolarClassloader getClassloaderOf(String name) {
        if (!pluginManager.isPluginLoaded(name)) {
            return null;
        }
        return (PolarClassloader) pluginManager.getPlugin(name).getClass().getClassLoader();
    }

    /**
     * 加载插件，不会自动加载依赖
     *
     * @param file 插件
     * @return 插件对象
     * @throws InvalidPluginException
     * @throws DependencyMissingException
     */
    @Override
    public Plugin loadPlugin(File file) throws InvalidPluginException, DependencyMissingException {
        PluginDescription description = getDescriptionOf(file);
        if (description == null) {
            throw new InvalidPluginException("Failed to load " + file.getAbsolutePath());
        }
        if (pluginManager.checkDependencies(Map.of(description.getName(), description)).size() != 0) {
            throw new DependencyMissingException("Dependency missing for " + description.getName());
        }
        try {
            PolarClassloader cl = new PolarClassloader(new URL[]{file.toURI().toURL()}, this, description.getName());
            Class<?> pluginClass = cl.findClass(description.getMain(), false);
            Plugin plugin = (Plugin) pluginClass.getConstructor().newInstance();
            if (description.isAutoRegister()) {
                Event.registerListeners(plugin);
            }
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
            //pluginMap.put(description.getName(), plugin);
            return plugin;
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
}
