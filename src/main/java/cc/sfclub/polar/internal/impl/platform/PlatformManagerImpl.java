package cc.sfclub.polar.internal.impl.platform;

import cc.sfclub.polar.api.platfrom.IPlatform;
import cc.sfclub.polar.api.platfrom.IPlatformBot;
import cc.sfclub.polar.api.platfrom.IPlatformManager;

import java.util.HashMap;
import java.util.Map;

public class PlatformManagerImpl implements IPlatformManager {
    private Map<String, IPlatformBot> bots = new HashMap<>();

    @Override
    public void register(IPlatformBot bot) {
        bots.put(bot.getUID(), bot);
    }

    @Override
    public IPlatform getByName(String name) {
        return bots.get(name).getPlatform();
    }
}
