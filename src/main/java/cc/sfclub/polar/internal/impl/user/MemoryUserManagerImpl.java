package cc.sfclub.polar.internal.impl.user;

import cc.sfclub.polar.api.platfrom.PlatformIdentifier;
import cc.sfclub.polar.api.user.IUserManager;
import cc.sfclub.polar.api.user.User;
import cc.sfclub.polar.api.user.UserGroup;
import cc.sfclub.polar.api.user.data.UserData;
import cc.sfclub.polar.api.user.perm.Perm;

import java.util.*;

public class MemoryUserManagerImpl implements IUserManager {
    private Map<PlatformIdentifier,User> userMap = new HashMap<>();
    private Map<Integer, UserData> userDatas = new HashMap<>();
    private Map<String,UserGroup> groupMap = new HashMap<>();
    @Override
    public User newUser(PlatformIdentifier identifier, UserData existedUserData) {
        return userMap.computeIfAbsent(identifier,k->new UserImpl(existedUserData,identifier));
    }

    @Override
    public User newUser(PlatformIdentifier identifier, boolean createUserData) {
        return createUserData?newUser(identifier,new UserData()):userMap.get(identifier);
    }

    @Override
    public UserGroup newGroup(String groupName, Perm... initialPerms) {
        return new GroupImpl(Set.of(initialPerms),null,groupName,Collections.emptyList());
    }

    @Override
    public User searchUserByID(PlatformIdentifier id) {
        return userMap.get(id);
    }

    @Override
    public UserData searchUserDataByID(int id) {
        return userDatas.get(id);
    }

    @Override
    public boolean existsUser(PlatformIdentifier identifier) {
        return userMap.containsKey(identifier);
    }

    @Override
    public boolean existsUserData(int id) {
        return userDatas.containsKey(id);
    }

    @Override
    public boolean existsName(String name) {
        return false;
    }

    @Override
    public User searchUserByPlatformID(PlatformIdentifier identifier) {
        return userMap.get(identifier);
    }

    @Override
    public UserGroup searchGroupByName(String name) {
        return groupMap.get(name);
    }

    @Override
    public void save(User u) {

    }

}
