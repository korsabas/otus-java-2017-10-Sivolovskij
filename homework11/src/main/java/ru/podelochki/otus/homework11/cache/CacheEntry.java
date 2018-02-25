package ru.podelochki.otus.homework11.cache;

import java.util.Objects;

public class CacheEntry<K,V> {
    private final K key;
    private final V value;

    public CacheEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CacheEntry) {
			return Objects.equals(this.key, ((CacheEntry) obj).key);
		} else {
			return super.equals(obj);
		}
	}

}
