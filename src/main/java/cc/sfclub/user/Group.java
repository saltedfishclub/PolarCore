package cc.sfclub.user;

import cc.sfclub.core.Core;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
@Table("userGroup")
public class Group implements Permissible {
    public static final Group DEFAULT = new Group("_");
    @Name
    @Deprecated
    @Setter
    private String name;
    @Deprecated
    @Setter
    @NonNull
    private List<Perm> permList = new ArrayList<>();
    @Setter
    private String extend;

    /**
     * Only for ORM Initial.(NoArgConstructor required)
     */
    @Deprecated
    public Group() {
        name = null;
    }

    public Group(String name, Perm... InitialPerms) {
        permList.addAll(Arrays.asList(InitialPerms));
        this.name = name;
    }

    public static Optional<Group> getGroup(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(Core.get().ORM().fetch(Group.class, Cnd.where("name", "=", name)));
    }

    public static Group getDefault() {
        return getGroup(Core.get().permCfg().getDefaultGroup()).orElse(DEFAULT);
    }

    @Override
    public boolean hasPermission(Perm perm) {
        if ("_".equals(name)) {
            return permList.contains(perm);
        }
        Optional<Group> father = getGroup(extend);
        return father.orElse(getDefault()).hasPermission(perm) || permList.contains(perm);
    }

    @Override
    public void addPermission(Perm perm) {
        permList.add(perm);
    }

    @Override
    public void delPermission(Perm perm) {
        permList.remove(perm);
    }
}
