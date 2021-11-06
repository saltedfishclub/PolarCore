package cc.sfclub.polar.user;

import cc.sfclub.polar.platfrom.PlatformIdentifier;
import cc.sfclub.polar.user.data.UserData;
import cc.sfclub.polar.user.perm.Perm;

import java.util.Set;

public interface User extends Permissible {

    UserData getData();

    PlatformIdentifier getPlatformId();

    default boolean hasPermission(Perm perm) {
        return getData().hasPermission(perm);
    }


    default boolean hasPermission(String perm) {
        return getData().hasPermission(perm);
    }


    default Set<Perm> getPermissions() {
        return getData().getPermissions();
    }

    public void delPermission();


    default void addPermissions(Perm... perms) {
        getData().addPermissions(perms);
    }

}
