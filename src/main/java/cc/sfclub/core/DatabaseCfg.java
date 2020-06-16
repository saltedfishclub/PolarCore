package cc.sfclub.core;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

@Getter
public class DatabaseCfg extends JsonConfig {
    public String host = "localhost:3306";
    public String user = "root";
    public String password = "qaq";
    public String database = "polar";

    public DatabaseCfg() {
        super(".");
    }

    @Override
    public String getConfigName() {
        return "database.json";
    }
}
