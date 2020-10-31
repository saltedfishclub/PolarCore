package cc.sfclub.core;

import cc.sfclub.util.common.JsonConfig;
import lombok.Getter;

@SuppressWarnings("all")
@Getter
public class DatabaseCfg extends JsonConfig {
    private String jdbcUrl = "jdbc:sqlite:data.db"; //Mysql: jdbc:mysql://localhost:3306/XX
    private String driver = "org.sqlite.JDBC";
    private String user = "";
    private String password = "";

    public DatabaseCfg() {
        super(".");
    }

    @Override
    public String getConfigName() {
        return "database.json";
    }
}
