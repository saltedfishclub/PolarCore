package cc.sfclub.module;

import cc.sfclub.module.exceptions.NameAlreadyLoaded;
import lombok.Getter;
import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Set;

public class Manager {
    @Getter
    private static final Manager inst = new Manager();
    private final HashMap<String, Module> modules = new HashMap<>();

    private Manager() {
    }

    public boolean addModule(Module module) throws NameAlreadyLoaded {
        if (module.getDescription() == null) {
            return false;
        }
        if (modules.containsKey(module.getDescription().name)) {
            throw new NameAlreadyLoaded(module.getDescription().name);
        }
        modules.put(module.getDescription().name, module);
        EventBus.getDefault().register(inst);
        return true;
    }

    public void delModule(String module) {
        modules.remove(module);
        EventBus.getDefault().unregister(modules.get(module));
    }

    public boolean exists(String module) {
        return modules.containsKey(module);
    }

    public Set<String> getModules() {
        return modules.keySet();
    }
}
