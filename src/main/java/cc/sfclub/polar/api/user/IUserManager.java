package cc.sfclub.polar.api.user;

import cc.sfclub.polar.api.platfrom.PlatformIdentifier;
import cc.sfclub.polar.api.user.data.UserData;
import cc.sfclub.polar.api.user.perm.Perm;

public interface IUserManager {
    User newUser(PlatformIdentifier identifier, UserData existedUserData);

    User newUser(PlatformIdentifier identifier, boolean createUserData);

    UserGroup newGroup(String groupName, Perm... initialPerms);

    User searchUserByID(PlatformIdentifier id);

    UserData searchUserDataByID(int id);

    /**
     * For platform id.
     *
     * @param identifier
     * @return
     */
    boolean existsUser(PlatformIdentifier identifier);

    boolean existsUserData(int id);

    boolean existsName(String name);

    User searchUserByPlatformID(PlatformIdentifier identifier);

    UserGroup searchGroupByName(String name);

    void save(User u);
}
