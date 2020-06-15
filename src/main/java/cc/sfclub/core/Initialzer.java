package cc.sfclub.core;

import cc.sfclub.core.modules.Core;
import cc.sfclub.core.modules.I18N;

public class Initialzer {
    private Initialzer() {
    }

    public static void main(String[] args) {
        System.out.println("\n" + "________      ______             _________                  \n" +
                "___  __ \\________  /_____ _________  ____/_________________ \n" +
                "__  /_/ /  __ \\_  /_  __ `/_  ___/  /    _  __ \\_  ___/  _ \\\n" +
                "_  ____// /_/ /  / / /_/ /_  /   / /___  / /_/ /  /   /  __/\n" +
                "/_/     \\____//_/  \\__,_/ /_/    \\____/  \\____//_/    \\___/ \n" +
                "                                                            \n");
        Core.getLogger().info(I18N.get().server.STARTING, Core.CORE_VERSION);
        //load Core modules
        Core.getLogger().info(I18N.get().server.LOAD_MODULE, "Core", Core.CORE_VERSION);
        Core core = new Core();

    }
}
