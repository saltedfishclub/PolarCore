package cc.sfclub.polar.user;

import cc.sfclub.polar.platfrom.PlatformIdentifier;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.user.perm.Perm;

public interface IUserManager {
    public User newUser(PlatformIdentifier identifier, UserData existedUserData);

    public User newUser(PlatformIdentifier identifier, boolean createUserData);

    public UserGroup newGroup(String groupName, Perm... initialPerms);

    public User searchUserByID(int id);

    public UserData searchUserDataByID(int id);

    /**
     * For platform id.
     *
     * @param identifier
     * @return
     */
    public boolean existsUser(String identifier);

    /**
     * For polar user id.
     *
     * @param id
     * @return
     */
    public boolean existsUser(int id);

    public boolean existsUserData(int id);

    public boolean existsName(String name);

    public User searchUserByPlatformID(String identifier);

    public UserGroup searchGroupByName(String name);

    public void save(User u);
}
