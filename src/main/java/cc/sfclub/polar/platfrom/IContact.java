package cc.sfclub.polar.platfrom;

import cc.sfclub.polar.user.Permissible;
import cc.sfclub.polar.user.User;

/**
 * 联系人对象，已确认好友关系。
 */
public interface IContact extends IMessageSource, Permissible {
    String getNickName();
    boolean blocked();
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
