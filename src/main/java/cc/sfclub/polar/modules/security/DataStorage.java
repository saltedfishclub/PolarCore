package cc.sfclub.polar.modules.security;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class DataStorage {
    /**
     * @return initial Priority for per new user.
     */
    private int initialPriority = 5;
    private int securityLevel = 1;
    /**
     * @return max queue length.
     */
    private int max_cache = 6;
    /**
     * @return addition delay for timestamp checker.
     */
    private int additionDelay = 200;
    private ConcurrentHashMap<String, Integer> Priority = new ConcurrentHashMap<>();
}
