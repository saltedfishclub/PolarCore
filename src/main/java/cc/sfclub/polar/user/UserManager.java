package cc.sfclub.polar.user;

import cc.sfclub.polar.platfrom.IPlatform;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.user.perm.Perm;
import io.ebean.Database;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserManager {
    private final Database db;
    public User newUser(String platformId, IPlatform platform, UserData existedUserData){
        // 构造新的用户
        User u = new User();
        u.setPlatform(platform);
        u.setPlatformIdentifier(platformId);
        u.setUserData(existedUserData);
        db.insert(u);
        return u;
    }
    public User newUser(int platformId,IPlatform platform,boolean createUserData){
        //// TODO: 02/08/2021
        return null;
    }
    public UserGroup newGroup(String groupName, Perm... initialPerms){
        return null;
    }
    public User searchUserByID(int id){
        return null;
    }
    public UserData searchUserDataByID(int id){
        return null;
    }

    /**
     * For platform id.
     * @param identifier
     * @return
     */
    public boolean existsUser(String identifier){
        return false;
    }

    /**
     * For polar user id.
     * @param id
     * @return
     */
    public boolean existsUser(int id){
        return false;
    }
    public boolean existsUserData(int id){
        return false;
    }
    public boolean existsName(String name){
        return false;
    }
    public User searchUserByPlatformID(String identifier){
        return null;
    }
    public UserGroup searchGroupByName(String name){
        return null;
    }
    public void save(User u){

    }
}
