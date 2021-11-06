package cc.sfclub.polar.api.user;

import cc.sfclub.polar.api.platfrom.PlatformIdentifier;
import cc.sfclub.polar.api.user.data.UserData;
import cc.sfclub.polar.api.user.perm.Perm;

public interface IUserManager {
    User newUser(PlatformIdentifier identifier, UserData existedUserData);

    User newUser(PlatformIdentifier identifier, boolean createUserData);

    UserGroup newGroup(String groupName, Perm... initialPerms);

    User searchUserByID(int id);

    UserData searchUserDataByID(int id);

    /**
     * For platform id.
     *
     * @param identifier
     * @return
     */
    boolean existsUser(String identifier);

    /**
     * For polar user id.
     *
     * @param id
     * @return
     */
    boolean existsUser(int id);

    boolean existsUserData(int id);

    boolean existsName(String name);

    User searchUserByPlatformID(String identifier);

    UserGroup searchGroupByName(String name);

    void save(User u);
}
