package cc.sfclub.user.perm;

/**
 * 可以被权限度量的对象
 */
public interface Permissible {
    boolean hasPermission(Perm perm);

    void addPermission(Perm perm);

    void delPermission(Perm perm);
}
