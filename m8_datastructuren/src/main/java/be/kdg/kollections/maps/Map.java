package be.kdg.kollections.maps;

import be.kdg.kollections.Collection;
import be.kdg.kollections.sets.Set;

public interface Map<K, V> {
    void put(K key, V value);
    V get(K key);
    Collection<V> values();
    Set<K> keySet();
    int size();
}
