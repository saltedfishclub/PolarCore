package cc.sfclub.user;

import cc.sfclub.user.perm.Perm;
import cc.sfclub.user.perm.Permissible;
import lombok.Getter;
import lombok.Setter;
import org.nutz.dao.entity.annotation.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Table
public class User implements Permissible {
    private final String UniqueID = UUID.randomUUID().toString();
    private final String group;
    private final List<Perm> permList = new ArrayList<>();
    @Setter
    private String userName;

    public User(String Group) {
        group = Group;
    }

    public User(String group, Perm... InitialPermissions) {
        this.group = group;
        permList.addAll(Arrays.asList(InitialPermissions));
    }

    @Override
    public boolean hasPermission(Perm perm) {
        if (Group.getGroup(getGroup()).orElse(Group.DEFAULT).hasPermission(perm)) {
            return true;
        } else return permList.contains(perm);
    }

    @Override
    public boolean hasPermission(String perm) {
        if (Group.getGroup(getGroup()).orElse(Group.DEFAULT).hasPermission(perm)) {
            return true;
        } else return permList.contains(perm);
    }

    @Override
    public void addPermission(Perm perm) {
        permList.add(perm);
    }

    @Override
    public void delPermission(String perm) {
        permList.remove(new Perm(perm));
    }

    @Override
    public void delPermission(Perm perm) {
        permList.remove(perm);
    }
}
