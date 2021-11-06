package cc.sfclub.polar.api.platfrom;

import cc.sfclub.polar.api.user.Permissible;
import cc.sfclub.polar.api.user.User;

/**
 * 联系人对象，已确认好友关系。
 */
public interface IContact extends IMessageSource, Permissible {
    String getNickName();
    String getUserName();
    boolean blocked();
    int id();
    /**
     * Nullable.
     * @return
     */
    IMember toMember(AbstractChatGroup group);
    /**
     * Nullable.
     * @return
     */
    User asUser();
}
