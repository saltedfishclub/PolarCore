package cc.sfclub.polar.modules.security;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

@Data
public class DataStorage {
    private int initialPriority = 5;
    private int securityLevel = 1;
    private int max_cache = 6;
    private int additionDelay = 200;
    private ConcurrentHashMap<String, Integer> Priority = new ConcurrentHashMap<>();
}
