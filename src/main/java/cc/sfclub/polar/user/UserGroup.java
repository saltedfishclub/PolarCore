package cc.sfclub.polar.user;

import cc.sfclub.polar.user.data.UserData;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface UserGroup extends Permissible {
    @Nullable
    UserGroup getParent();

    String name();

    Collection<? extends UserData> getMembers();
}
