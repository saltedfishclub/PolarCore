package cc.sfclub.polar.internal.impl;

import cc.sfclub.polar.api.Bot;
import cc.sfclub.polar.api.IEventChannel;
import cc.sfclub.polar.api.platfrom.IPlatformManager;
import cc.sfclub.polar.api.user.IUserManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PolarBot implements Bot {
    private final IUserManager userManager;
    private final IPlatformManager platformManager;
    private final IEventChannel eventChannel;
}
