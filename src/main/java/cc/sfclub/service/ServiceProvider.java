package cc.sfclub.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    private static final Map<String, Registry<?>> services = new HashMap<>();

    public static <T> void setRegistry(Class<T> service, Registry<T> registry) {
        services.put(service.getCanonicalName(), registry);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> service) {
        return (T) services.get(service.getCanonicalName()).get();
    }
}
