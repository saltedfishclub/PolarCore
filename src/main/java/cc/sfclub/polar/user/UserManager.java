package cc.sfclub.polar.user;

import cc.sfclub.polar.platfrom.IPlatform;
import cc.sfclub.polar.user.data.User;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.user.data.query.QUser;
import cc.sfclub.polar.user.data.query.QUserData;
import cc.sfclub.polar.user.perm.Perm;
import io.ebean.Database;
import io.ebean.Ebean;
import io.ebean.Query;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserManager {
    private final Database db;

    public User newUser(String platformId, IPlatform platform, UserData existedUserData) {
        User user = new User();
        user.setPlatform(platform);
        user.setPlatformIdentifier(platformId);
        user.setUserData(existedUserData);
        db.insert(user);

        return user;
    }
    public User newUser(int platformId,IPlatform platform,boolean createUserData) {
        User user = new User();
        user.setPlatformIdentifier(String.valueOf(platformId));
        user.setPlatform(platform);

        if (createUserData) {
            user.setUserData(new UserData());
        }
        db.insert(user);

        return user;
    }

    public UserGroup newGroup(String groupName, Perm... initialPerms) {
        UserGroup userGroup = new UserGroup(groupName, initialPerms);
        db.insert(userGroup);

        return userGroup;
    }

    public User searchUserByID(int id) {
        QUser qUser = QUser.alias();

        User user = new QUser()
                .select(qUser.id, qUser.userData, qUser.platform, qUser.platformIdentifier)
                .id.eq(id)
                .findOne();

        return user;
    }

    public UserData searchUserDataByID(int id) {
        QUserData qUserData = QUserData.alias();

        UserData userData = new QUserData()
                .select(qUserData.id, qUserData.userGroup, qUserData.nickName, qUserData.permissionNodes, qUserData.registrationTime)
                .id.eq(id)
                .findOne();

        return userData;
    }

    /**
     * For platform id.
     * @param identifier
     * @return
     */
    public boolean existsUser(String identifier) {
        return searchUserByPlatformID(identifier) != null;
    }

    /**
     * For polar user id.
     * @param id
     * @return
     */
    public boolean existsUser(int id) {
        return searchUserByID(id) != null;
    }

    public boolean existsUserData(int id) {
        return searchUserDataByID(id) != null;
    }

    public boolean existsName(String name) {
        QUserData qUserData = QUserData.alias();

        UserData userData = new QUserData()
                .select(qUserData.id, qUserData.userGroup, qUserData.nickName, qUserData.permissionNodes, qUserData.registrationTime)
                .nickName.eq(name)
                .findOne();

        return userData != null;
    }

    public User searchUserByPlatformID(String identifier) {
        QUser qUser = QUser.alias();

        User user = new QUser()
                .select(qUser.id, qUser.userData, qUser.platform, qUser.platformIdentifier)
                .platformIdentifier.eq(identifier)
                .findOne();

        return user;
    }

    //TODO Fix EBean generate issue
    public UserGroup searchGroupByName(String name) {
        return null;
    }

    public void save(User u) {

    }
}
