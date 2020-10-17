package cc.sfclub.user;

import cc.sfclub.core.Core;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import lombok.Data;
import lombok.Setter;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Table

public class User implements Permissible {
    /**
     * UID，Setter仅提供给ORM使用
     */
    @Deprecated
    @Name
    @Setter
    private String uniqueID = UUID.randomUUID().toString();
    /**
     * 用户组名
     */
    private String userGroup;
    /**
     * Setter仅提供给ORM使用，权限列表
     */
    @Deprecated
    @Setter
    private List<Perm> permList = new ArrayList<>();
    /**
     * 用户名
     */
    private String userName;
    /**
     * 来源平台
     */
    private String platfrom;
    /**
     * 在进行权限判断，信息获取的时候会跳转到这个变量索引的User。
     * 用于进行跨平台同步一用户。使用UUID
     */
    private String redirectTo;

    public User() {

    }

    public User(String Group) {
        userGroup = Group;
    }

    public User(String group, Perm... InitialPermissions) {
        this.userGroup = group;
        permList.addAll(Arrays.asList(InitialPermissions));
    }

    public User(String Group, String platfrom) {
        this.platfrom = platfrom;
        userGroup = Group;
    }

    public static User byName(String userName) {
        return Core.get().ORM().fetch(User.class, Cnd.where("userName", "=", userName));
    }

    public static User byUUID(String userId) {
        return Core.get().ORM().fetch(User.class, Cnd.where("uniqueID", "=", userId));
    }

    @Override
    public boolean hasPermission(Perm perm) {
        if (redirectTo == null) {
            if (Group.getGroup(getUserGroup()).orElse(Group.DEFAULT).hasPermission(perm)) {
                return true;
            } else return permList.contains(perm);
        }
        return byUUID(redirectTo).hasPermission(perm);
    }

    @Override
    public void addPermission(Perm perm) {
        if (redirectTo == null) {
            permList.add(perm);
        } else {
            byUUID(redirectTo).addPermission(perm);
        }
    }
    @Override
    public void delPermission(Perm perm) {
        if (redirectTo != null) byUUID(redirectTo).addPermission(perm);
        else {
            permList.remove(perm);
        }
    }
}
