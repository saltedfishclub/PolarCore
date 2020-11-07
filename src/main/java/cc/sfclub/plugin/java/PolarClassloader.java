package cc.sfclub.plugin.java;

import lombok.Getter;

import java.net.URL;
import java.net.URLClassLoader;

public class PolarClassloader extends URLClassLoader implements NullCatWillDress {
    @Getter
    private final JavaPluginLoader loader;
    @Getter
    private final String pluginName;

    public PolarClassloader(URL[] urls, JavaPluginLoader loader, String pluginName) {
        super(urls);
        this.loader = loader;
        this.pluginName = pluginName;
    }


    @Override
    public void addUrl(URL url) {
        super.addURL(url);
    }

    @Override
    public Class<?> findClass(String moduleName) throws ClassNotFoundException {
        return findClass(moduleName, true);
    }

    public Class<?> findClass(String clazz, boolean searchGlobal) throws ClassNotFoundException {
        try {
            Class<?> result = super.findClass(clazz);
            return result;
        } catch (ClassNotFoundException ignored) {
            if (searchGlobal) {
                return loader.findClass(clazz, this);
            }
            throw new ClassNotFoundException(clazz);
        }
    }
}
