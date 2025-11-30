package be.kdg.kollections;

public interface Collection<E> {
    void add(E element);
    boolean remove(E element);
    boolean contains(E element);
    int size();
}
