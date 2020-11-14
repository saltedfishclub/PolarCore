package cc.sfclub.user.exception;

import cc.sfclub.user.User;

public class UserPlatformUnboundException extends NullPointerException {
    public UserPlatformUnboundException(User u) {
        super("User " + u + " haven't bind to available platform or platform not found(" + u.getPlatform() + ")");
    }
}
