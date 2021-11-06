package cc.sfclub.polar.user.data;


import cc.sfclub.polar.platfrom.PlatformIdentifier;
import cc.sfclub.polar.user.Permissible;
import cc.sfclub.polar.user.UserGroup;
import cc.sfclub.polar.user.perm.Perm;
import lombok.Getter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


@Getter
public class UserData implements Permissible {

    private String nickName;
    private Instant registrationTime;

    private List<PlatformIdentifier> relatedUsers;
    private Set<Perm> permissionNodes = new LinkedHashSet<>();

    private UserGroup userGroup;

    @Override
    public boolean hasPermission(Perm perm) {
        //// TODO: 02/08/2021
        return permissionNodes.contains(perm);
    }

    @Override
    public boolean hasPermission(String perm) {
        return permissionNodes.contains(Perm.of(perm));
    }

    @Override
    public Set<Perm> getPermissions() {
        return permissionNodes;
    }

    @Override
    public void delPermission() {
        ///todo
    }

    @Override
    public void addPermissions(Perm... perms) {

    }
}
