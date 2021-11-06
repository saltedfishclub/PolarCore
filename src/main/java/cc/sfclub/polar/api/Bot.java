package cc.sfclub.polar.api;

import cc.sfclub.polar.api.platfrom.IPlatformManager;
import cc.sfclub.polar.api.user.IUserManager;
import cc.sfclub.polar.internal.loader.Loader;

public interface Bot {
    static Bot getInstance() {
        return Loader.botInst;
    }

    IUserManager getUserManager();

    IPlatformManager getPlatformManager();

    IEventChannel getEventChannel();

}
