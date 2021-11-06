package cc.sfclub.polar;

import cc.sfclub.polar.platfrom.PlatformManager;
import cc.sfclub.polar.user.IUserManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Bot {
    private final IUserManager userManager;
    private final PlatformManager platformManager;
    private final EventChannel eventChannel;
    protected static Bot instance;

    public static Bot getInstance() {
        return instance;
    }

    public static IUserManager getUserManager() {
        return getInstance().userManager;
    }

    public static PlatformManager getPlatformManager() {
        return getInstance().platformManager;
    }

    public static EventChannel getEventChannel() {
        return getInstance().eventChannel;
    }

}
