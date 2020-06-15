package cc.sfclub.module;

import cc.sfclub.core.modules.Core;

import java.io.*;

public class Config {
    private transient String root;

    public Config(String rootDir) {
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
    public Config reloadConfig() {
        try {
            BufferedInputStream f = new BufferedInputStream(new FileInputStream(root + "/" + getConfigName()));
            int size = f.available();
            StringBuilder confText = new StringBuilder();
            for (int i = 0; i < size; i++) {
                confText.append((char) f.read());
            }
            Config pluginConfig = Core.getGson().fromJson(confText.toString(), this.getClass());
            pluginConfig.root = root;
            return pluginConfig;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}