package cc.sfclub.polar.utils;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.User;
import org.nutz.dao.Cnd;

public class UserUtil {
    private UserUtil() {
    }

    public static User getUser(long uid, String Provider) {
        return Core.getInstance().getDao().fetch(User.class, Cnd.where("Provider", "=", Provider).and("UID", "=", uid));
    }

    public static void addUser(User u) {
        Core.getInstance().getDao().insert(u);
        Core.getLogger().info("New User: " + u.toString());
    }

    public static boolean isUserExists(long UID, String Provider) {
        return getUser(UID, Provider) != null;
    }

    public static void delUser(long uid, String Provider) {
        if (isUserExists(uid, Provider)) {
            Core.getInstance().getDao().clear(User.class, Cnd.where("UniqueID", "=", getUser(uid, Provider).getUniqueID()));
        }
    }
}

