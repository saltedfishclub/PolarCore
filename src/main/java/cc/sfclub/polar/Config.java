package cc.sfclub.polar;

import cc.sfclub.polar.user.Group;

import java.util.ArrayList;

class database {
    public String host;
    public String user;
    public String password;
    public String database;
}

public class Config {
    public transient String defaultGroup;
    public int config_version;
    public String version;
    public String name;
    public Boolean debug;
    public String startsWith;
    public boolean useWhiteList;
    public ArrayList<String> WhiteGroups = new ArrayList<>();
    public database database = new database();
    public ArrayList<Group> groups = new ArrayList<>();
}
