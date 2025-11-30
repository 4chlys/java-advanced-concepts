package be.kdg.kollections.maps;

import be.kdg.kollections.lists.ArrayList;
import be.kdg.kollections.lists.List;
import be.kdg.kollections.sets.ArraySet;
import be.kdg.kollections.sets.Set;

public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 100;

    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node<K, V>[] buckets;
    private int size = 0;

    public HashMap() {
        this(DEFAULT_CAPACITY);
    }

    public HashMap(int capacity) {
        buckets = new Node[capacity];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    @Override
    public void put(K key, V value) {
        for (int i = 0; i < buckets.length; i++) {
            Node<K, V> bucket = buckets[i];
            if (bucket == null) {
                buckets[i] = new Node<>(key, value);
                size++;
                return;
            } else if (bucket.key.equals(key)) {
                bucket.value = value;
                return;
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V get(K key) {
        for (Node<K, V> bucket : buckets) {
            if (bucket != null && bucket.key.equals(key)) {
                return bucket.value;
            }
        }

        return null;
    }

    @Override
    public List<V> values() {
        List<V> values = new ArrayList<>(size);
        for (Node<K, V> bucket : buckets) {
            Node<K, V> node = bucket;
            while (node != null) {
                values.add(node.value);
                node = node.next;
            }
        }
        return values;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new ArraySet<>();
        for (Node<K, V> bucket : buckets) {
            Node<K, V> node = bucket;
            while (node != null) {
                keySet.add(node.key);
                node = node.next;
            }
        }
        return keySet;
    }
}
