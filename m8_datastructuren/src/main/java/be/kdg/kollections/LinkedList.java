package be.kdg.kollections;


public class LinkedList<E> implements List<E> {
    static class Node<V> {
        V value;
        Node<V> next;

        public Node(V value) {
            this.value = value;
        }
    }

    private Node<E> root;
    private int size;

    public LinkedList() {
    }

    @Override
    public void add(int index, E element) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }

        Node<E> newNode = new Node<>(element);

        if (index == 0) {
            newNode.next = root;
            root = newNode;
        } else {
            Node<E> beforeNode = root;
            for (int i = 1; i < index; i++) {
                beforeNode = beforeNode.next;
            }
            newNode.next = beforeNode.next;
            beforeNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(E element) {
        add(size, element);
    }

    @Override
    public void set(int index, E element) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        Node<E> node = root;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        node.value = element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        if (index == 0) {
            E oldElement = root.value;
            root = root.next;
            size--;
            return oldElement;
        } else {
            Node<E> beforeNode = root;
            for (int i = 1; i < index; i++) {
                beforeNode = beforeNode.next;
            }
            E oldElement = beforeNode.next.value;
            beforeNode.next = beforeNode.next.next;
            size--;
            return oldElement;
        }
    }

    @Override
    public E get(int index) {
        if (index >= this.size || index < 0) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
        }
        Node<E> node = root;
        if (index != 0) {
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        }
        return node.value;
    }
}
