package cc.sfclub.polar;

import cc.sfclub.polar.user.Group;

import java.util.ArrayList;

class Database {
    /**
     * host. (host:port)
     */
    public String host = "localhost:3306";
    /**
     * username
     */
    public String user = "root";
    /**
     * password
     */
    public String password = "qaq";
    /**
     * database.
     */
    public String database = "polar";
}

public class Config {
    /**
     * default permission group(pGroup)
     */
    public transient String defaultGroup;
    /**
     * config version
     */
    public int config_version;
    /**
     * bot version
     */
    public String version = "dev";
    /**
     * bot name
     */
    public String name = "Polar";
    /**
     * debug mode(print some useful info for debugging)
     */
    public Boolean debug = false;
    /**
     * command head
     */
    public String startsWith = "!p";
    /**
     * use polar security
     */
    public boolean usePolarSecurity = true;
    /**
     * database connection info
     */
    public Database database = new Database();
    /**
     * permission groups(pGroup)
     */
    public ArrayList<Group> groups = new ArrayList<>();
}
