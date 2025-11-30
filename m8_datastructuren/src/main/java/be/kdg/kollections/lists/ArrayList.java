package be.kdg.kollections.lists;

import be.kdg.kollections.Kollections;

public class ArrayList<E> implements List<E> {
    private static final int INITIAL_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public ArrayList() {
        this(INITIAL_CAPACITY);
    }

    public ArrayList(int capacity) {
        elements = new Object[capacity];
        size = 0;
    }

    private void expand() {
        Object[] newElements = new Object[elements.length * 2];
        System.arraycopy(elements, 0, newElements, 0, elements.length);
        elements = newElements;
    }

    @Override
    public void add(int index, E element) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        if (size == elements.length) {
            expand();
        }
        if (size - index >= 0) System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }

    @Override
    public boolean remove(E element) {
        int index = Kollections.lineairSearch(this, element);
        if (index!=-1) {
            remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element)!=-1;
    }

    @Override
    public int indexOf(E element) {
        return Kollections.lineairSearch(this, element);
    }

    @Override
    public void set(int index, E element) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        elements[index] = element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        E oldValue = (E) elements[index];

        if (size - 1 - index >= 0) System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        size--;
        return oldValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        return (E) elements[index];
    }
}
