package cc.sfclub.polar.api.user;

import cc.sfclub.polar.api.user.perm.Perm;

import java.util.Set;

public interface Permissible {
    boolean hasPermission(Perm perm);

    boolean hasPermission(String perm);

    Set<Perm> getPermissions();

    void delPermission();
    void addPermissions(Perm... perms);
}
