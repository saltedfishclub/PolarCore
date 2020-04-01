package org.mve.plugin.java;

import com.google.gson.Gson;

import java.io.*;

public class PluginConfig {
    private transient JavaPlugin plugin;
    private transient Gson gson = new Gson();

    public PluginConfig(JavaPlugin p) {
        plugin = p;
    }

    public String getConfigName() {
        return "config.json";
    }

    /**
     * Save Config to config.json
     */
    public void saveConfig() {
        try {
            byte[] bWrite = gson.toJson(this).getBytes();
            File conf = new File(plugin.getDataFolder().getAbsolutePath() + "/" + getConfigName());
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
            BufferedInputStream f = new BufferedInputStream(new FileInputStream(plugin.getDataFolder().getAbsolutePath() + "/" + getConfigName()));
            int size = f.available();
            StringBuilder confText = new StringBuilder();
            for (int i = 0; i < size; i++) {
                confText.append((char) f.read());
            }
            return gson.fromJson(confText.toString(), this.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
