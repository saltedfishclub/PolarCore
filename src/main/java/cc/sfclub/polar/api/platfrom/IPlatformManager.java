package cc.sfclub.polar.api.platfrom;

public interface IPlatformManager {
    void register(IPlatformBot bot);

    IPlatform getByName(String name);
}
