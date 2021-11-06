package cc.sfclub.polar.user.data;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * Utility class that helps you fetch.
 */
@RequiredArgsConstructor
public class UserID {
    private final UUID uuid;

    public UUID getUUID() {
        return uuid;
    }

    public UserData getUser() {

    }
}
