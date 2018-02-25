package ru.podelochki.otus.homework11.cache;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class CacheImpl<K,V> implements Cache<K,V> {

    private static final int DELAY = 10000;
    private final Map<K, SoftReference<CacheEntry<K,V>>> cache = new LinkedHashMap<>();
    private int hitsCount = 0;
    private int missCount = 0;
    private int maxSize = 1000;


    public CacheImpl() {
        Timer cleanTimer = new Timer();
        cleanTimer.schedule(cleanCache(), DELAY);
    }
    public CacheImpl(int maxSize) {
        Timer cleanTimer = new Timer();
        cleanTimer.schedule(cleanCache(), DELAY);
        this.maxSize = maxSize;
    }

    @Override
    public void put(CacheEntry<K, V> cacheEntry) {
    	if (cache.size() == maxSize) {
    		cleanCache().run();
    		Iterator<K> it = cache.keySet().iterator();
    		if (cache.size() == maxSize && it.hasNext()) {
    			K element = it.next();
    			cache.remove(element);
    		}
    	}
        if (!Objects.equals(get(cacheEntry.getKey()),cacheEntry)){
            cache.put(cacheEntry.getKey(),new SoftReference<>(cacheEntry));
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
    public void printInfo() {
        System.out.println(String.format("TotalCount: %s \n Hit: %s \n Miss: %s", hitsCount + missCount, hitsCount, missCount));
    }
}
