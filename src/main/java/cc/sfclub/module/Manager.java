package cc.sfclub.module;

import cc.sfclub.core.I18N;
import cc.sfclub.core.modules.Core;
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
        if (module.description == null) {
            return false;
        }
        if (modules.containsKey(module.description.name)) {
            throw new NameAlreadyLoaded(module.description.name);
        }
        modules.put(module.description.name, module);
        EventBus.getDefault().register(module);
        Core.getLogger().info(I18N.get().server.LOAD_MODULE, module.description.name, module.description.version);
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
