package cc.sfclub.polar.user;

import cc.sfclub.polar.user.perm.Perm;

import java.util.Set;

public interface Permissible {
    boolean hasPermission(Perm perm);

    boolean hasPermission(String perm);

    Set<Perm> getPermissions();

    void delPermission();
    void addPermissions(Perm... perms);
}
