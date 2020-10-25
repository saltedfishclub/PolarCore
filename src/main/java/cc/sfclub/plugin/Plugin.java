package cc.sfclub.plugin;

import cc.sfclub.command.Source;
import cc.sfclub.core.Core;
import cc.sfclub.plugin.java.PolarClassloader;
import cc.sfclub.transform.Bot;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Getter
@Setter
public abstract class Plugin {
    private PluginDescription description;
    private SimpleConfig<?> config;
    private File dataFolder;
    private boolean loaded = false;

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> t) {
        ClassLoader loader = t.getClassLoader();
        if (loader instanceof PolarClassloader) {
            PolarClassloader pcl = ((PolarClassloader) loader);
            return (T) pcl.getLoader().getPluginByName(pcl.getPluginName());
        }
        return null;
    }

    public Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    public void registerBot(Bot bot) {
        Core.get().registerBot(bot);
    }

    public void registerCommand(LiteralArgumentBuilder<Source> commandTree) {
        Core.get().dispatcher().register(commandTree);
    }

    public String getCoreVersion() {
        return Core.CORE_VERSION;
    }
}
