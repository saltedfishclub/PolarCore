package cc.sfclub.user.perm;

public interface Permissible {
    boolean hasPermission(Perm perm);

    boolean hasPermission(String perm);

    void addPermission(Perm perm);

    void delPermission(String perm);

    void delPermission(Perm perm);
}
