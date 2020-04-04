package org.mve.plugin.java;

import cc.sfclub.polar.Core;

import java.io.*;

public class PluginConfig {
    private transient String root;

    public PluginConfig(JavaPlugin p) {
        root = p.getDataFolder().getAbsolutePath();
    }

    public PluginConfig(String rootDir) {
        root = rootDir;
    }

    public String getConfigName() {
        return "config.json";
    }

    /**
     * Save Config to config.json
     */
    public void saveConfig() {
        try {
            byte[] bWrite = Core.getGson().toJson(this).getBytes();
            File conf = new File(root + "/" + getConfigName());
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(conf));
            os.write(bWrite);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reload Config
     */
    public PluginConfig reloadConfig() {
        try {
            BufferedInputStream f = new BufferedInputStream(new FileInputStream(root + "/" + getConfigName()));
            int size = f.available();
            StringBuilder confText = new StringBuilder();
            for (int i = 0; i < size; i++) {
                confText.append((char) f.read());
            }
            PluginConfig pluginConfig = Core.getGson().fromJson(confText.toString(), this.getClass());
            pluginConfig.root = root;
            return pluginConfig;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
