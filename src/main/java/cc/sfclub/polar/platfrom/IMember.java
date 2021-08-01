package cc.sfclub.polar.platfrom;

import cc.sfclub.polar.user.Permissible;
import cc.sfclub.polar.user.User;

/**
 * 群成员。
 */
public interface IMember extends IMessageSource, Permissible {
    boolean reachable();
    String getNickName();
    String getUserName();
    String getHonor();

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
