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

    @Override
    public boolean hasPermission(Perm perm) {
        if ("_".equals(name)) {
            return permList.contains(perm);
        }
        Optional<Group> father = Core.get().userManager().getGroup(extend);
        return father.orElse(Core.get().userManager().getDefault()).hasPermission(perm) || permList.contains(perm);
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
