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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheEntry)) return false;

        CacheEntry<?, ?> element = (CacheEntry<?, ?>) o;

        if (getKey() != null ? !getKey().equals(element.getKey()) : element.getKey() != null) return false;
        return getValue() != null ? getValue().equals(element.getValue()) : element.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = getKey() != null ? getKey().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }

}
