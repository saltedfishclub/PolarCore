package cc.sfclub.polar.internal.impl.user;

import cc.sfclub.polar.api.platfrom.PlatformIdentifier;
import cc.sfclub.polar.api.user.User;
import cc.sfclub.polar.api.user.data.UserData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserImpl implements User {
    private final UserData userData;
    private final PlatformIdentifier platformId;

    @Override
    public UserData getData() {
        return userData;
    }

    @Override
    public PlatformIdentifier getPlatformId() {
        return platformId;
    }
}
