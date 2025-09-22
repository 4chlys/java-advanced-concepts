package be.kdg.generics;

import java.util.*;

public class PriorityQueue<T> implements FIFOQueue<T> {
    private TreeMap<Integer, LinkedList<T>> priorityMap;
    private int totalSize;

    /**
     * Constructor - initializes the TreeMap with reverse order (highest priority first)
     */
    public PriorityQueue() {
        priorityMap = new TreeMap<>(Comparator.reverseOrder());
        totalSize = 0;
    }

    /**
     * Adds an element to the queue with the given priority
     *
     * @param element  The element to be added
     * @param priority The priority (1-5)
     * @return true if successfully added, false if the element already exists
     */
    @Override
    public boolean enqueue(T element, int priority) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("priority must be between 1 and 5");
        }

        if (containsElement(element)) {
            return false;
        }

        LinkedList<T> priorityList = priorityMap.get(priority);
        if (priorityList == null) {
            priorityList = new LinkedList<>();
            priorityMap.put(priority, priorityList);
        }

        priorityList.addLast(element);
        totalSize++;
        return true;
    }

    /**
     * Removes and returns the element with highest priority (FIFO within priority)
     *
     * @return The element with highest priority, or null if empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        Map.Entry<Integer, LinkedList<T>> highestPriorityEntry = priorityMap.firstEntry();
        LinkedList<T> highestPriorityList = highestPriorityEntry.getValue();

        T element = highestPriorityList.removeFirst();
        totalSize--;

        if (highestPriorityList.isEmpty()) {
            priorityMap.remove(highestPriorityEntry.getKey());
        }

        return element;
    }

    @Override
    public int search(Object element) {
        int position = 1;

        for (Map.Entry<Integer, LinkedList<T>> entry : priorityMap.entrySet()) {
            LinkedList<T> priorityList = entry.getValue();
            int indexInList = priorityList.indexOf(element);
            if (indexInList != -1) {
                return position + indexInList;
            }

            position += priorityList.size();
        }
        return -1;
    }

    @Override
    public int getSize() {
        return totalSize;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "PriorityQueue: empty";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, LinkedList<T>> entry : priorityMap.entrySet()) {
            LinkedList<T> list = entry.getValue();
            for (T element : list) {
                sb.append(entry.getKey()).append(": ").append(element).append("\n");
            }
        }
        return sb.toString();
    }

    public boolean containsElement(Object element) {
        for (LinkedList<T> list : priorityMap.values()) {
            if (list.contains(element)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return totalSize == 0;
    }
}