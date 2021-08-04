package cc.sfclub.polar.user;

import cc.sfclub.polar.platfrom.IPlatform;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.user.perm.Perm;
import org.jetbrains.annotations.ApiStatus;

//// TODO: 02/08/2021
public class UserManager {
    public User newUser(int platformId, IPlatform platform, UserData existedUserData){
        //// TODO: 02/08/2021
    }
    public User newUser(int platformId,IPlatform platform,boolean createUserData){
        //// TODO: 02/08/2021
    }
    public UserGroup newGroup(String groupName, Perm... initialPerms){

    }
    public User searchUserByID(int id){

    }
    public UserData searchUserDataByID(int id){

    }

    /**
     * For platform id.
     * @param identifier
     * @return
     */
    public boolean existsUser(String identifier){

    }

    /**
     * For polar user id.
     * @param id
     * @return
     */
    public boolean existsUser(int id){

    }
    public boolean existsUserData(int id){

    }
    public boolean existsName(String name){

    }
    public User searchUserByPlatformID(String identifier){

    }
    public UserGroup searchUserByGroup(String name){

    }
    public void save(User u){

    }
}
