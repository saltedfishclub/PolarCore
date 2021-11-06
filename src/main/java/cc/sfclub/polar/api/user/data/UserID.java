package cc.sfclub.polar.api.user.data;

import cc.sfclub.polar.api.Bot;
import lombok.RequiredArgsConstructor;

/**
 * Utility class that helps you fetch.
 */
@RequiredArgsConstructor
public class UserID {
    private final int id;

    public int getId() {
        return id;
    }

    public UserData getUser() {
        return Bot.getInstance().getUserManager().searchUserDataByID(id);
    }
}
