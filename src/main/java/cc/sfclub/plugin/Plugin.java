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
    /**
     * 获取插件Logger
     *
     * @return
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean loaded = false;
    /**
     * Plugin 静态方法，用于获取到插件实例
     *
     * @param t
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> t) {
        ClassLoader loader = t.getClassLoader();
        if (loader instanceof PolarClassloader) {
            PolarClassloader pcl = ((PolarClassloader) loader);
            return (T) pcl.getLoader().getPluginManager().getPlugin(pcl.getPluginName());
        }
        return null;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void registerBot(Bot bot) {
        Core.get().registerBot(bot);
    }

    /**
     * 注册命令
     *
     * @param commandTree 命令树
     */
    public void registerCommand(LiteralArgumentBuilder<Source> commandTree) {
        Core.get().dispatcher().register(commandTree);
    }

    /**
     * 获取PolarCore版本
     *
     * @return
     */
    public String getCoreVersion() {
        return Core.CORE_VERSION;
    }
}
