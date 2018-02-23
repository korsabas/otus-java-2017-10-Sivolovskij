package ru.podelochki.otus.homework12.cache;

public interface Cache<K, V> {

    void put(CacheEntry<K,V> cacheElement);
    CacheEntry<K,V> get(K key);
    int getHitCount();
    int getMissCount();
    String printInfo();
    
}
