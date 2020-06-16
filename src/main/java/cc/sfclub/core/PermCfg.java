package cc.sfclub.core;

import cc.sfclub.user.Group;
import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PermCfg extends JsonConfig {
    private final String defaultGroup = "_";
    private final List<Group> groupList = new ArrayList<>();

    public PermCfg() {
        super(".");
    }

    @Override
    public String getConfigName() {
        return "groups.json";
    }
}
