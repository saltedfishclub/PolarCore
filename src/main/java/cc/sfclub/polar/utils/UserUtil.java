package cc.sfclub.polar.utils;

import cc.sfclub.polar.Core;
import cc.sfclub.polar.user.User;
import org.nutz.dao.Cnd;

public class UserUtil {
    private UserUtil() {
    }

    /**
     * nullable.get a user from database.(NO CACHE)
     *
     * @param uid      user id
     * @param provider provider
     * @return nullable user
     */
    public static User getUser(long uid, String provider) {
        return Core.getInstance().getDao().fetch(User.class, Cnd.where("Provider", "=", provider).and("UID", "=", uid));
    }

    /**
     * nullable.get a user from database.(NO CACHE)
     *
     * @param uniqueID polarCore user uuid
     * @return nullable user
     */
    public static User getUser(String uniqueID) {
        return Core.getInstance().getDao().fetch(User.class, Cnd.where("uniqueID", "=", uniqueID));
    }

    /**
     * add a user
     *
     * @param user user
     */
    public static void addUser(User user) {
        Core.getInstance().getDao().insert(user);
        Core.getLogger().info("New User: " + user.toString());
    }

    /**
     * check user exists.
     *
     * @param UID      uid
     * @param provider provider
     * @return result
     */
    public static boolean isUserExists(long UID, String provider) {
        return getUser(UID, provider) != null;
    }

    /**
     * check user exists.
     *
     * @param uniqueID polarCore user uuid
     * @return nullable user
     */
    public static boolean isUserExists(String uniqueID) {
        return getUser(uniqueID) != null;
    }

    /**
     * Delete user.
     *
     * @param uid      user id
     * @param provider provider
     */
    public static void delUser(long uid, String provider) {
        if (isUserExists(uid, provider)) {
            Core.getInstance().getDao().clear(User.class, Cnd.where("UniqueID", "=", getUser(uid, provider).getUniqueID()));
        }
    }
}

