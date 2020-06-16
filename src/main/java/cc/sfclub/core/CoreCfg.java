package cc.sfclub.core;

import cc.sfclub.core.modules.Core;
import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

@Getter
public class CoreCfg extends JsonConfig {
    private final String commandPrefix = "!p";
    private final String locale = "en_US";
    private final int config_version = Core.CONFIG_VERSION;
    private final boolean debug = false;
    private final String name = "Polar";
    private final String version = "v4-production";

    public CoreCfg() {
        super(".");
    }
}
