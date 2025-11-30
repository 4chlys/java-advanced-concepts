package be.kdg.kollections.lists;

import be.kdg.kollections.Collection;

public interface List<E> extends Collection<E> {
    void add(int index, E element);
    void set(int index, E element);
    E remove(int index);
    int indexOf(E element);
    E get(int index);
}
