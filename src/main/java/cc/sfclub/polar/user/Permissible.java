package cc.sfclub.polar.user;

import cc.sfclub.polar.user.perm.Perm;

import java.util.List;

public interface Permissible {
    boolean hasPermission(Perm perm);
    boolean hasPermission(String perm);
    List<Perm> getPermissions();
}
