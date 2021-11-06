package cc.sfclub.polar.platfrom;

import cc.sfclub.polar.user.User;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class PlatformIdentifier {
    private final String identifier;
    private final IPlatform platform;

    public Optional<User> fetchUser() {
        return platform.getBots().stream().map(e -> e.contactByID(identifier)).filter(Objects::nonNull).map(IContact::asUser).findFirst();
    }
}
