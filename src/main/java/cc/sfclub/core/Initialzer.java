package cc.sfclub.core;

import cc.sfclub.core.modules.Core;
import cc.sfclub.core.modules.I18N;
import cc.sfclub.user.Group;
import cc.sfclub.user.User;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.util.common.SimpleFile;
import com.alibaba.druid.pool.DruidDataSource;

public class Initialzer {
    private Initialzer() {
    }

    public static void main(String[] args) {
        System.out.println("\n" + "________      ______             _________                  \n" +
                "___  __ \\________  /_____ _________  ____/_________________ \n" +
                "__  /_/ /  __ \\_  /_  __ `/_  ___/  /    _  __ \\_  ___/  _ \\\n" +
                "_  ____// /_/ /  / / /_/ /_  /   / /___  / /_/ /  /   /  __/\n" +
                "/_/     \\____//_/  \\__,_/ /_/    \\____/  \\____//_/    \\___/ \n" +
                "                                                            \n");
        Core.getLogger().info(I18N.get().server.STARTING, Core.CORE_VERSION);
        Core.getLogger().info(I18N.get().server.LOAD_MODULE, "Core", Core.CORE_VERSION);
        loadCore();
    }

    private static void loadCore() {
        //Load Configs
        boolean firstLoad = false;
        CoreCfg cfg = new CoreCfg();
        DatabaseCfg dbCfg = new DatabaseCfg();
        PermCfg permCfg = new PermCfg();
        if (!SimpleFile.exists(cfg.getConfigName()) || !SimpleFile.exists(permCfg.getConfigName()) || !SimpleFile.exists(dbCfg.getConfigName())) {
            firstLoad = true;
        }
        cfg = (CoreCfg) cfg.saveDefaultOrLoad();
        dbCfg = (DatabaseCfg) dbCfg.saveDefaultOrLoad();
        permCfg = (PermCfg) permCfg.saveDefaultOrLoad();
        if (firstLoad) {
            Core.getLogger().info(I18N.get().server.FIRST_START);
            System.exit(0);
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://" + dbCfg.host + "/" + dbCfg.database);
        dataSource.setUsername(dbCfg.user);
        dataSource.setPassword(dbCfg.password);
        new Core(cfg, permCfg, dataSource);
        if (!Core.getCore().getORM().exists(User.class)) {
            Core.getLogger().warn(I18N.get().exceptions.TABLE_NOT_FOUND, User.class.getName());
            Core.getCore().getORM().create(User.class, false);
            User console = new User(null, new Perm(".*"));
            console.setUserName("CONSOLE");
            Core.getCore().getORM().insert(console);
        }
        if (!Core.getCore().getORM().exists(Group.class)) {
            Core.getLogger().warn(I18N.get().exceptions.TABLE_NOT_FOUND, Group.class.getName());
            Core.getCore().getORM().create(Group.class, false);
            permCfg.getGroupList().forEach(Core.getCore().getORM()::insert);
        }
    }

}
