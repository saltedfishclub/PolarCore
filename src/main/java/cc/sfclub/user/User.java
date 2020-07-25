package cc.sfclub.user;

import cc.sfclub.core.Core;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import lombok.Getter;
import lombok.Setter;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Table
public class User implements Permissible {
    @Deprecated
    @Name
    @Setter
    private String uniqueID = UUID.randomUUID().toString();
    @Setter
    private String userGroup;
    @Deprecated
    @Setter
    private List<Perm> permList = new ArrayList<>();
    @Setter
    private String userName;

    public User() {

    }

    public User(String Group) {
        userGroup = Group;
    }

    public User(String group, Perm... InitialPermissions) {
        this.userGroup = group;
        permList.addAll(Arrays.asList(InitialPermissions));
    }

    public static User byName(String userName) {
        return Core.get().ORM().fetch(User.class, Cnd.where("userName", "=", userName));
    }

    public static User byUUID(String userId) {
        return Core.get().ORM().fetch(User.class, Cnd.where("uniqueID", "=", userId));
    }

    @Override
    public boolean hasPermission(Perm perm) {
        if (Group.getGroup(getUserGroup()).orElse(Group.DEFAULT).hasPermission(perm)) {
            return true;
        } else return permList.contains(perm);
    }

    @Override
    public boolean hasPermission(String perm) {
        if (Group.getGroup(getUserGroup()).orElse(Group.DEFAULT).hasPermission(perm)) {
            return true;
        }
        for (Perm perm1 : permList) {
            if (perm1.equals(perm)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addPermission(Perm perm) {
        permList.add(perm);
    }

    @Override
    public void delPermission(String perm) {
        permList.remove(new Perm(perm));
    }

    @Override
    public void delPermission(Perm perm) {
        permList.remove(perm);
    }
}
