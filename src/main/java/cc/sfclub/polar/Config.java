package cc.sfclub.polar;

import cc.sfclub.polar.user.Group;

import java.util.ArrayList;

class Database {
    public String host = "localhost:3306";
    public String user = "root";
    public String password = "qaq";
    public String database = "polar";
}

public class Config {
    public transient String defaultGroup;
    public int config_version;
    public String version = "dev";
    public String name = "Polar";
    public Boolean debug = false;
    public String startsWith = "!p";
    public boolean usePolarSecurity = true;
    public Database database = new Database();
    public ArrayList<Group> groups = new ArrayList<>();
}
