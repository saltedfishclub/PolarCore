package cc.sfclub.polar.user;

import cc.sfclub.polar.database.converter.PermListConverter;
import cc.sfclub.polar.user.perm.Perm;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Table(name = "userGroup")
@Getter
public class UserGroup implements Permissible{
    public static final UserGroup DEFAULT = new UserGroup("_");
    @NonNull
    @Deprecated
    @Convert(converter = PermListConverter.class)
    private List<Perm> permList = new LinkedList<>();
    @Id
    @Setter
    private String name;
    /**
     * 父租
     */
    @Setter
    private String extend;

    protected UserGroup(String name, Perm... InitialPerms) {
        permList.addAll(Arrays.asList(InitialPerms));
        this.name = name;
    }

    @Override
    public boolean hasPermission(Perm perm) {
        if ("_".equals(name)) {
            return permList.contains(perm);
        }
        //todo Optional<UserGroup> father = Core.get().userManager().getGroup(extend);
        // return father.orElse(DEFAULT).hasPermission(perm) || permList.contains(perm);
        return false;
    }

    public boolean hasPermission(String perm) {
        return hasPermission(Perm.of(perm));
    }

    @Override
    public List<Perm> getPermissions() {
        return null;
    }

    @Override
    public void delPermission() {

    }

    @Override
    public void addPermissions(Perm... perm) {
        for (Perm perm1 : perm) {
            if (!hasPermission(perm1)) {
                permList.add(perm1);
            }
        }
    }
}
