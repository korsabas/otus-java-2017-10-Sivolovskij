package ru.podelochki.otus.homework12.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class CacheImpl<K,V> implements Cache<K,V> {

    private static final int DELAY = 10000;
    private Map<K, SoftReference<CacheEntry<K,V>>> cache = new HashMap<>();
    private int hitsCount = 0;
    private int missCount = 0;


    public CacheImpl() {
        Timer cleanTimer = new Timer();
        cleanTimer.schedule(cleanCache(), DELAY);
    }

    @Override
    public void put(CacheEntry<K, V> CacheEntry) {
        if (!get(CacheEntry.getKey()).equals(CacheEntry)){
            cache.put(CacheEntry.getKey(),new SoftReference<>(CacheEntry));
        }

    }

    @Override
    public CacheEntry<K, V> get(K key) {
        CacheEntry<K, V> CacheEntry = null;
        if (cache != null && cache.get(key) != null) CacheEntry = cache.get(key).get();

        if (cache != null && cache.size() > 0){
            if (CacheEntry != null) {
                hitsCount++;
            } else {
                missCount++;
            }
        }
        return CacheEntry == null ? new CacheEntry<>(null, null) : CacheEntry;
    }


    @Override
    public int getHitCount() {
        return hitsCount;
    }

    @Override
    public int getMissCount() {
        return missCount;
    }

    private TimerTask cleanCache() {
        return new TimerTask() {
            @Override
            public void run() {
                List<K> emptyKeys = cache.entrySet().stream()
                        .filter(c -> c.getValue().get() == null)
                        .map(Map.Entry::getKey).collect(Collectors.toList());
                emptyKeys.forEach(k -> cache.remove(k));
            }
        };
    }

    @Override
    public String printInfo() {
        System.out.println(String.format("TotalCount: %s \n Hit: %s \n Miss: %s", hitsCount + missCount, hitsCount, missCount));
        return String.format("TotalCount: %s \n Hit: %s \n Miss: %s", hitsCount + missCount, hitsCount, missCount);
    }
}
