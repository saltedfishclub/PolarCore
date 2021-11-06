package cc.sfclub.polar.internal.impl.user;

import cc.sfclub.polar.api.user.UserGroup;
import cc.sfclub.polar.api.user.data.UserData;
import cc.sfclub.polar.api.user.perm.Perm;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class GroupImpl implements UserGroup {
    private final Set<Perm> perms;
    private final UserGroup parent;
    private final String name;
    private final List<UserData> users;

    @Override
    public boolean hasPermission(Perm perm) {
        return perms.contains(perm);
    }

    @Override
    public Set<Perm> getPermissions() {
        return perms;
    }

    @Override
    public void delPermission(Perm perm) {
        perms.remove(perm);
    }

    @Override
    public void addPermissions(Perm... perms) {
        this.perms.addAll(List.of(perms));
    }

    @Override
    public @Nullable UserGroup getParent() {
        return parent;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Collection<? extends UserData> getMembers() {
        return users;
    }
}
