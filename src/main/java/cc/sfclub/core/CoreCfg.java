package cc.sfclub.core;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CoreCfg extends JsonConfig {
    private String commandPrefix = "!p";
    private String locale = "en_US";
    private int config_version = Core.CONFIG_VERSION;
    private boolean debug = true;
    private String name = "Polar";
    private String version = "v4-production";
    @Setter
    private boolean resetDatabase = true;

    public CoreCfg() {
        super(".");
    }
}
