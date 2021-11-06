package cc.sfclub.polar.api.platfrom;

import cc.sfclub.polar.api.user.Permissible;
import cc.sfclub.polar.api.user.User;

/**
 * 群成员。
 */
public interface IMember extends IMessageSource, Permissible {
    boolean reachable();
    String getNickName();
    String getUserName();
    String getHonor();
    int id();
    /**
     * Nullable.
     * @return
     */
    User asUser();
    /**
     * Nullable.
     * @return
     */
    IContact asContact();
}
