package cc.sfclub.user;

import cc.sfclub.core.Core;
import cc.sfclub.user.perm.Perm;
import com.dieselpoint.norm.Database;

import java.util.Optional;

/**
 * 用户管理器
 */
public class UserManager {
    private final Database db;

    public UserManager(Database database) {
        this.db = database;
    }

    /**
     * 用用户名获取用户对象，Nullable
     *
     * @param userName
     * @return
     */
    public User byName(String userName) {
        return preprocess(db.where("userName=?", userName).first(User.class));
    }

    /**
     * 用UUID获取用户对象，Nullable
     *
     * @param userId
     * @return
     */
    public User byUUID(String userId) {
        return preprocess(db.where("uniqueId=?", userId).first(User.class));
    }

    /**
     * 用平台名+平台专属ID获取用户，nullable
     *
     * @param platform
     * @param id
     * @return
     */
    public User byPlatformID(String platform, String id) {
        return preprocess(db.where("platform=? AND platformId=?", platform, id).first(User.class));
    }

    /**
     * 设置用户的"指针"
     * 大部分关于这个用户的操作将会被重定向到目标上
     *
     * @param redirected
     * @param to
     * @return
     */
    public User setRedirectUser(User redirected, User to) {
        redirected.setRedirectTo(to.getUniqueID());
        redirected.setRealUser(to);
        return redirected;
    }

    private User preprocess(User u) {
        if (u == null) return null;
        if (u.getRedirectTo() != null) {
            u.setRealUser(byUUID(u.getRedirectTo()));
        }
        return u;
    }

    /**
     * 判断用户ID是否存在
     *
     * @param userId
     * @return
     */
    public boolean existsId(String userId) {
        return db.where("uniqueID=?", userId) == null;
    }

    /**
     * 判断是否存在用户名
     */
    public boolean existsName(String userName) {

        return db.table("User").where("userName=?", userName).results(User.class).size() != 0;
    }

    public void addRaw(User u) {
        db.insert(u);
    }

    public void update(User u) {
        db.update(u);
    }

    /**
     * 注册用户
     *
     * @param group    权限组，可null
     * @param platform 平台
     * @param id       平台ID
     * @return
     */
    public User register(String group, String platform, String id) {
        User user = new User(group, platform, id);
        db.insert(user);
        return user;
    }

    /**
     * 注册用户
     *
     * @param group              权限组
     * @param initialPermissions 初始权限
     * @return
     */
    public User register(String group, Perm... initialPermissions) {
        User user = new User(group, initialPermissions);
        db.insert(user);
        return user;
    }

    public void addRaw(Group group) {
        db.insert(group);
    }

    /**
     * 注册权限组，已经存在的话会返回存在的
     *
     * @param name
     * @param InitialPerms
     * @return
     */
    public Group registerGroup(String name, Perm... InitialPerms) {
        Optional<Group> i = getGroup(name);
        if (i.isPresent()) {
            return i.get();
        }
        Group group = new Group(name, InitialPerms);
        Core.get().ORM().insert(group);
        return group;
    }

    /**
     * 获取权限组
     *
     * @param name
     * @return
     */
    public Optional<Group> getGroup(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(db.where("name=?", name).first(Group.class));
    }

    /**
     * 取默认
     *
     * @return
     */
    public Group getDefault() {
        return getGroup(Core.get().permCfg().getDefaultGroup()).orElse(Group.DEFAULT);
    }
}
