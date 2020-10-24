package cc.sfclub.user;

import cc.sfclub.core.Core;
import cc.sfclub.database.converter.StrListConverter;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import com.dieselpoint.norm.Query;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity(name = "user")
//@Table(name="user")
public class User implements Permissible {
    /**
     * 权限列表
     */
    @Column(name = "permList")
    @Convert(converter = StrListConverter.class)
    public List<Perm> permList = new LinkedList<>();
    /**
     * 用户组名
     */
    private String userGroup;
    /**
     * UID
     */
    private String uniqueID = UUID.randomUUID().toString();
    /**
     * 用户名
     */
    @Setter
    private String userName;
    /**
     * 来源平台
     */
    private String platform;
    /**
     * 来源平台分配的ID
     */
    private String platformId;
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

    public User(String Group, String platform, String platformId) {
        this.platformId = platformId;
        this.platform = platform;
        userGroup = Group;
    }

    public static User byName(String userName) {
        return Core.get().ORM().where("userName=?", userName).first(User.class);
    }

    public static User byUUID(String userId) {
        return Core.get().ORM().where("uniqueId=?", userId).first(User.class);
    }

    public static User byPlatformID(String platform, String id) {
        return Core.get().ORM().where("platform=? AND platformId=?", platform, id).first(User.class);
    }

    public static boolean existsId(String userId) {
        return Core.get().ORM().where("uniqueID=?", userId) == null;
    }

    public static boolean existsName(String userName) {

        return Core.get().ORM().table("User").where("userName=?", userName) == null;
    }

    public static Query addRaw(User u) {
        return Core.get().ORM().insert(u);
    }

    public static Query update(User u) {
        return Core.get().ORM().update(u);
    }

    public static User register(String group, String platform, String id) {
        User user = new User(group, platform, id);
        Core.get().ORM().insert(user);
        return user;
    }

    public static User register(String group, Perm... initialPermissions) {
        User user = new User(group, initialPermissions);
        Core.get().ORM().insert(user);
        return user;
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
