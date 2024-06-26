package com.example.demo.finance.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MyLocalCache<K,V>{

    private final int capacity;

    private final LinkedHashMap<K,V>map;


    public MyLocalCache(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<K, V>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                //如果大于容量，自动移除最早的
                return size() > MyLocalCache.this.capacity;
            }
        };
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void put(K key, V value) {
        map.put(key, value);
    }

    public synchronized Map.Entry<K, V> pop() {
        if (map.isEmpty()) {
            return null;
        }
        Map.Entry<K, V> newest = null;
        for (Map.Entry<K, V> kvEntry : map.entrySet()) {
            newest = kvEntry;
        }
        if (newest != null) {
            map.remove(newest.getKey());
        }
        return newest;
    }

    public synchronized V compute(K key, V value, BiFunction<K, V, V> remappingFunction) {
        return map.compute(key, (k, v) -> remappingFunction.apply(k, (v == null) ? value : v));
    }

}
