package cc.sfclub.user;

import cc.sfclub.core.modules.Core;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.nutz.dao.Cnd;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Getter
@Table("group")
@RequiredArgsConstructor
public class Group implements Permissible {
    public static final Group DEFAULT = new Group("_", null, Collections.emptyList());
    @Name
    private final String name;
    @NonNull
    private final List<Perm> permList;
    @Setter
    @NonNull
    private String extend;

    public static Optional<Group> getGroup(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(Core.getCore().getORM().fetch(Group.class, Cnd.where("name", "=", name)));
    }

    public static Group getDefault() {
        return getGroup(Core.getCore().getPermCfg().getDefaultGroup()).orElse(DEFAULT);
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
    public boolean hasPermission(String perm) {
        return permList.contains(perm);
    }

    @Override
    public void addPermission(Perm perm) {
        permList.add(perm);
    }

    @Override
    public void delPermission(String perm) {
        delPermission(new Perm(perm));
    }

    @Override
    public void delPermission(Perm perm) {
        permList.remove(perm);
    }
}
