package cc.sfclub.plugin;

import cc.sfclub.core.Core;
import cc.sfclub.core.I18N;
import lombok.Getter;
import lombok.SneakyThrows;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

public class PluginManager {
    @Getter
    private static final PluginManager inst = new PluginManager();
    private Map<String, Plugin> pluginMap = new HashMap<>();

    private PluginManager() {
    }

    @SneakyThrows
    public Optional<Plugin> loadPlugin(File file, JarFile jarFile) {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        String main = jarFile.getManifest().getMainAttributes().getValue("Main-Class");
        String name = jarFile.getManifest().getMainAttributes().getValue("Plugin-Name");
        String version = jarFile.getManifest().getMainAttributes().getValue("Plugin-Ver");
        if (main == null || name == null || version == null) {
            Core.getLogger().warn(I18N.get().misc.COULD_NOT_LOAD_PLUGIN, file.toString());
            return Optional.empty();
        }
        Plugin plugin = (Plugin) urlClassLoader.loadClass(main).getDeclaredConstructor().newInstance();
        File datadir = new File("./plugins/" + name + "/");
        plugin.setDataFolder(datadir);
        datadir.mkdir();
        SimpleConfig simpleConfig = new SimpleConfig("./plugins/" + name);
        plugin.setSimpleConfig(simpleConfig);
        Description desc = new Description(name, version);
        plugin.setDescription(desc);
        boolean hasSubscriber = false;
        for (Method method : plugin.getClass().getDeclaredMethods()) {
            for (Annotation a : method.getDeclaredAnnotations()) {
                if (a.annotationType().getCanonicalName().equals("org.greenrobot.eventbus.Subscribe")) {
                    hasSubscriber = true;
                    break;
                }
            }
        }
        if (!hasSubscriber) {
            Core.getLogger().warn(I18N.get().exceptions.PLUGIN_NOT_SUBSCRIBER, name);
            return Optional.of(plugin);
        }
        pluginMap.put(name, plugin);
        EventBus.getDefault().register(plugin);
        return Optional.of(plugin);
    }

    public void unloadPlugin(String name) throws PluginNotLoaded {
        if (!isLoaded(name)) throw new PluginNotLoaded();
        EventBus.getDefault().unregister(pluginMap.get(name));
        pluginMap.remove(name);
    }

    public boolean isLoaded(String name) {
        return pluginMap.containsKey(name);
    }

    public Collection<Plugin> getPlugins() {
        return pluginMap.values();
    }

    public Set<String> getPluginNames() {
        return pluginMap.keySet();
    }

    public Optional<Plugin> getPlugin(String name) {
        return Optional.ofNullable(pluginMap.get(name));
    }
}
