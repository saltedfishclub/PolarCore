package cc.sfclub.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHook extends Thread {
    private final Logger logger = LoggerFactory.getLogger("ShutdownHook");

    @Override
    public void run() {
        logger.info("Interrupt signal received!");
        if (Core.get() == null) {
            logger.error("Core is null! Maybe polar didn't initialize completed or something wrong?");
            return;
        }
        Core.get().stop();
    }
}
