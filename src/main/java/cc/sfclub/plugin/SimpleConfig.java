package cc.sfclub.plugin;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class SimpleConfig extends JsonConfig {
    @Getter
    private final Map<String, String> settings = new HashMap<>();

    public SimpleConfig(String rootDir) {
        super(rootDir);
    }
}
