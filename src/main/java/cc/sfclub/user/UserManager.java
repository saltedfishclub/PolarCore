package cc.sfclub.user;

import cc.sfclub.core.Core;
import cc.sfclub.user.perm.Perm;
import com.dieselpoint.norm.Database;
import com.dieselpoint.norm.Query;

import java.util.Optional;

public class UserManager {
    private final Database db;

    public UserManager(Database database) {
        this.db = database;
    }

    public User byName(String userName) {
        return preprocess(db.where("userName=?", userName).first(User.class));
    }

    public User byUUID(String userId) {
        return preprocess(db.where("uniqueId=?", userId).first(User.class));
    }

    public User byPlatformID(String platform, String id) {
        return preprocess(db.where("platform=? AND platformId=?", platform, id).first(User.class));
    }

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
        u.setManager(this);
        return u;
    }

    public boolean existsId(String userId) {
        return db.where("uniqueID=?", userId) == null;
    }

    public boolean existsName(String userName) {

        return db.table("User").where("userName=?", userName).results(User.class).size() != 0;
    }

    public Query addRaw(User u) {
        return db.insert(u);
    }

    public Query update(User u) {
        return db.update(u);
    }

    public User register(String group, String platform, String id) {
        User user = new User(group, platform, id);
        db.insert(user);
        return user;
    }

    public User register(String group, Perm... initialPermissions) {
        User user = new User(group, initialPermissions);
        db.insert(user);
        return user;
    }

    public Group registerGroup(String name, Perm... InitialPerms) {
        Optional<Group> i = getGroup(name);
        if (i.isPresent()) {
            return i.get();
        }
        Group group = new Group(name, InitialPerms);
        Core.get().ORM().insert(group);
        return group;
    }

    public Optional<Group> getGroup(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(Core.get().ORM().where("name=?", name).first(Group.class));
    }

    public Group getDefault() {
        return getGroup(Core.get().permCfg().getDefaultGroup()).orElse(Group.DEFAULT);
    }
}
