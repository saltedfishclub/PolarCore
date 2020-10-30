package cc.sfclub.user;

import cc.sfclub.Internal;
import cc.sfclub.database.converter.PermListConverter;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity(name = "user")
//@Table(name="user")
public class User implements Permissible {
    /**
     * 权限列表
     */
    @Column(name = "permList")
    @Convert(converter = PermListConverter.class)
    public List<Perm> permList = new LinkedList<>();
    /**
     * 用户组名
     */
    @Getter
    @Setter
    private String userGroup;
    /**
     * UID
     */
    @Getter
    @Setter
    private String uniqueID = UUID.randomUUID().toString();
    /**
     * 用户名
     */
    @Getter
    @Setter
    private String userName;
    /**
     * 来源平台
     */
    @Getter
    @Setter
    private String platform;
    /**
     * 来源平台分配的ID
     */
    @Getter
    @Setter
    private String platformId;
    /**
     * 在进行权限判断，信息获取的时候会跳转到这个变量索引的User。
     * 用于进行跨平台同步一用户。使用UUID
     */
    @Getter
    private String redirectTo;
    @Setter(AccessLevel.PROTECTED)
    @Transient
    private User realUser;
    @Transient
    @Setter(AccessLevel.PROTECTED)
    private UserManager manager;

    @Internal
    public User() {

    }

    @Internal
    protected User(String Group) {
        userGroup = Group;
    }

    protected User(String group, Perm... InitialPermissions) {
        this.userGroup = group;
        permList.addAll(Arrays.asList(InitialPermissions));
    }

    protected User(String Group, String platform, String platformId) {
        this.platformId = platformId;
        this.platform = platform;
        userGroup = Group;
    }

    @Override
    public boolean hasPermission(Perm perm) {
        if (realUser == null) {
            if (manager.getGroup(getUserGroup()).orElse(Group.DEFAULT).hasPermission(perm)) {
                return true;
            } else return permList.contains(perm);
        }
        return realUser.hasPermission(perm);
    }

    @Override
    public void addPermission(Perm perm) {
        if (redirectTo == null) {
            permList.add(perm);
        } else {
            realUser.addPermission(perm);
        }
    }

    @Override
    public void delPermission(Perm perm) {
        if (redirectTo != null) realUser.addPermission(perm);
        else {
            permList.remove(perm);
        }
    }

    public String asFormattedName() {
        if (userName == null) {
            return uniqueID;
        }
        return userName + "(" + uniqueID + ")";
    }

    /**
     * 注意: 请使用UserManager.setRedirectUser(本对象，目标);
     *
     * @param s
     */
    @Internal
    public void setRedirectTo(String s) {
        this.redirectTo = s;
    }

    public void save() {
        manager.update(this);
    }
}
