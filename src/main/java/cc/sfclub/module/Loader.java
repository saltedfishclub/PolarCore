package cc.sfclub.module;

import cc.sfclub.module.exceptions.InvalidModule;
import lombok.SneakyThrows;

import java.io.File;

public class Loader {
    @SneakyThrows
    public Module loadFileAsModule(File module) throws InvalidModule {
        //todo
        return null;
    }

    @SneakyThrows
    public Module loadClassAsModule(String zlass) {
        Class clazz = Class.forName(zlass);
        return loadClassAsModule(clazz);
    }

    @SneakyThrows
    public Module loadClassAsModule(Class<? extends Module> blass) throws InvalidModule {
        Module inst = blass.getDeclaredConstructor().newInstance();
        Manager.getInst().addModule(inst);
        return inst;
    }
}
