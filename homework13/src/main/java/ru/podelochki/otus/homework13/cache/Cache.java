package ru.podelochki.otus.homework13.cache;

public interface Cache<K, V> {

    void put(CacheEntry<K,V> cacheElement);
    CacheEntry<K,V> get(K key);
    int getHitCount();
    int getMissCount();
    String printInfo();
    
}
