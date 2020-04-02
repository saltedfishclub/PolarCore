package cc.sfclub.polar.modules.security;

import cc.sfclub.polar.Core;
import com.google.gson.Gson;
import lombok.Getter;

import java.io.*;

/**
 * Polar Security Module.*anti-spam*
 */
public class PolarSec {
    @Getter
    private static DataStorage conf = new DataStorage();
    @Getter
    protected int busyLevel = 0;
    @Getter
    private MessageListener ml;
    private Gson Gson = new Gson();

    public void onEnable() {
        File config = new File("sec.json");
        //todo use PluginConfig
        if (!config.exists()) {
            saveConfig(config);
        } else {
            try {
                BufferedInputStream f = new BufferedInputStream(new FileInputStream("./sec.json"));
                int size = f.available();
                StringBuilder confText = new StringBuilder();
                for (int i = 0; i < size; i++) {
                    confText.append((char) f.read());
                }
                conf = Gson.fromJson(confText.toString(), DataStorage.class);
                if (conf == null) {
                    Core.getLogger().warn("[PolarSec] Nothing in the config.");
                    saveConfig(config);
                }
                Core.getLogger().info("[PolarSec] Config load successfully!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ml = new MessageListener(this);
        Core.getInstance().getMessage().register(ml);
        Core.getInstance().getCommandManager().register(new Sec());
    }

    public void onDisable() {
        ml.destory();
        File config = new File("sec.json");
        saveConfig(config);
    }

    public void saveConfig(File config) {
        try {
            byte[] bWrite = Gson.toJson(conf).getBytes();
            BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(config));
            for (byte b : bWrite) {
                os.write(b);
            }
            Core.getLogger().info("[PolarSec] Config saved.");
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            Core.getLogger().error("Failed to write config.");
        }
    }
}
