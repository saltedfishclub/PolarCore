package cc.sfclub.core;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

@Getter
public class DatabaseCfg extends JsonConfig {
    private final String jdbcUrl = "jdbc:sqlite:data.db"; //Mysql: jdbc:mysql://localhost:3306/XX
    private final String driver = "org.sqlite.JDBC";
    private final String user = "";
    private final String password = "";

    public DatabaseCfg() {
        super(".");
    }

    @Override
    public String getConfigName() {
        return "database.json";
    }
}
