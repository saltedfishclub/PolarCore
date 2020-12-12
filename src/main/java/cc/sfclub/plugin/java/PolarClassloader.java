package cc.sfclub.plugin.java;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSigner;
import java.security.CodeSource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@SuppressWarnings("deprecated")
public class PolarClassloader extends URLClassLoader implements NullCatWillDress {
    @Getter
    private final JavaPluginLoader loader;
    @Getter
    private final String pluginName;
    private final Map<String, Class<?>> classes = new ConcurrentHashMap<>();
    private final File plugin;
    private final JarFile jar;
    private final Manifest manifest;
    private final URL url;

    @SneakyThrows
    public PolarClassloader(File plugin, JavaPluginLoader loader, String pluginName) throws MalformedURLException {
        super(new URL[]{plugin.toURI().toURL()});
        this.loader = loader;
        this.pluginName = pluginName;
        this.plugin = plugin;
        this.jar = new JarFile(plugin);
        this.manifest = jar.getManifest();
        this.url = plugin.toURI().toURL();
    }

    public Set<String> getClasses() {
        return classes.keySet();
    }

    @Override
    public void addUrl(URL url) {
        super.addURL(url);
    }

    @Override
    public Class<?> findClass(String moduleName) throws ClassNotFoundException {
        return findClass(moduleName, true);
    }

    @SuppressWarnings("all")
    public Class<?> findClass(String clazz, boolean searchGlobal) throws ClassNotFoundException {
        Class<?> target = classes.get(clazz);
        if (target == null) {
            String path = clazz.replace('.', '/').concat(".class");
            JarEntry entry = jar.getJarEntry(path);

            if (entry != null) {
                byte[] classBytes;

                try (InputStream is = jar.getInputStream(entry)) {
                    classBytes = is.readAllBytes();
                } catch (IOException ex) {
                    throw new ClassNotFoundException(clazz, ex);
                }

                //classBytes = loader.getUnsafe().processClass(description, path, classBytes);

                int dot = clazz.lastIndexOf('.');
                if (dot != -1) {
                    String pkgName = clazz.substring(0, dot);
                    if (getPackage(pkgName) == null) {
                        try {
                            if (manifest != null) {
                                definePackage(pkgName, manifest, url);
                            } else {
                                definePackage(pkgName, null, null, null, null, null, null, null);
                            }
                        } catch (IllegalArgumentException ex) {
                            if (getPackage(pkgName) == null) {
                                throw new IllegalStateException("Cannot find package " + pkgName);
                            }
                        }
                    }
                }

                CodeSigner[] signers = entry.getCodeSigners();
                CodeSource source = new CodeSource(url, signers);
                target = defineClass(clazz, classBytes, 0, classBytes.length, source);
                if (target != null) {
                    classes.put(clazz, target);
                    return target;
                }
            }

            if (target == null) {
                try {
                    target = super.findClass(clazz);
                } catch (ClassNotFoundException ignored) {

                }
            }
            if (searchGlobal) {
                target = loader.findClass(clazz, this);
                return target;
            }
        }
        if (target != null) {
            return target;
        }
        throw new ClassNotFoundException("Couldn't find class " + clazz + " for plugin: " + pluginName);
    }
}
