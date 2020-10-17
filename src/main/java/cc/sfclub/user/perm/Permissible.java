package cc.sfclub.user.perm;

public interface Permissible {
    boolean hasPermission(Perm perm);

    void addPermission(Perm perm);

    void delPermission(Perm perm);
}
