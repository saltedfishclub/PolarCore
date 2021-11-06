package cc.sfclub.polar.api.user;

import cc.sfclub.polar.api.platfrom.PlatformIdentifier;
import cc.sfclub.polar.api.user.data.UserData;
import cc.sfclub.polar.api.user.perm.Perm;

import java.util.Set;

public interface User extends Permissible {

    UserData getData();

    PlatformIdentifier getPlatformId();

    default boolean hasPermission(Perm perm) {
        return getData().hasPermission(perm);
    }

    default Set<Perm> getPermissions() {
        return getData().getPermissions();
    }

    default void delPermission(Perm perm) {getData().delPermission(perm);}


    default void addPermissions(Perm... perms) {
        getData().addPermissions(perms);
    }

}
