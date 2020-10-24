package cc.sfclub.user;

import cc.sfclub.core.Core;
import cc.sfclub.database.converter.PermListConverter;
import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Table(name = "userGroup")
public class Group implements Permissible {
    public static final Group DEFAULT = new Group("_");
    @NonNull
    @Deprecated
    @Convert(converter = PermListConverter.class)
    public List<Perm> permList = new ArrayList<>();
    @Setter
    @Getter
    private String name;
    /**
     * 父租
     */
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

    public static Group register(String name, Perm... InitialPerms) {
        Optional<Group> i = getGroup(name);
        if (i.isPresent()) {
            return i.get();
        }
        Group group = new Group(name, InitialPerms);
        Core.get().ORM().insert(group);
        return group;
    }

    public static Optional<Group> getGroup(String name) {
        if (name == null) return Optional.empty();
        return Optional.ofNullable(Core.get().ORM().where("name=?", name).first(Group.class));
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
