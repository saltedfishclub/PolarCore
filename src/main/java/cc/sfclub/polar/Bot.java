package cc.sfclub.polar;

import cc.sfclub.polar.platfrom.PlatformManager;
import cc.sfclub.polar.user.UserManager;
import com.dieselpoint.norm.Database;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Bot {
    private final UserManager userManager;
    private final PlatformManager platformManager;
    private final Database database;
    private final EventChannel eventChannel;
    protected static Bot instance;
    public static Bot getInstance(){

    }
    public static UserManager getUserManager(){
        return getInstance().userManager;
    }
    public static PlatformManager getPlatformManager(){
        return getInstance().platformManager;
    }
    public static EventChannel getEventChannel(){
        return getInstance().eventChannel;
    }
}
