package cc.sfclub.polar.utils;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.User;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

public class UserUtil {
    private Dao DAO = Core.getDao();

    public User getUser(long uid, String Provider) {
        return DAO.fetch(User.class, Cnd.where("Provider", "=", Provider).and("UID", "=", uid));
    }

    public void addUser(User u) {
        DAO.insert(u);
        Core.getLogger().info("New User: " + u.toString());
    }

    public boolean isUserExists(long UID, String Provider) {
        return getUser(UID, Provider) != null;
    }

    public void delUser(long uid, String Provider) {
        if (isUserExists(uid, Provider)) {
            DAO.clear(User.class, Cnd.where("UniqueID", "=", getUser(uid, Provider).getUniqueID()));
        }
    }
}

