package cc.sfclub.module;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

public class Module {
    @Getter
    public Description description;
    @Getter
    public JsonConfig config;
}
