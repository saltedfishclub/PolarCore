package cc.sfclub.core;

import cc.sfclub.core.modules.Core;
import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

@Getter
public class CoreCfg extends JsonConfig {
    private String commandPrefix = "!p";
    private String locale = "en_US";
    private int config_version = Core.CONFIG_VERSION;
    private boolean debug = false;
    private String name = "Polar";
    private String version = "v4-production";

    public CoreCfg() {
        super(".");
    }
}