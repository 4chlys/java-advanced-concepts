package be.kdg;

import be.kdg.kollections.lists.ArrayList;
import be.kdg.kollections.Kollections;
import be.kdg.kollections.lists.LinkedList;
import be.kdg.kollections.lists.List;
import be.kdg.kollections.maps.HashMap;
import be.kdg.kollections.maps.ListMap;
import be.kdg.kollections.maps.Map;
import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikeFactory;

import java.util.Random;

public class PerformanceTester {

    public static List<RaceBike> randomList(int n) {
        List<RaceBike> myList = new LinkedList<>();
        new Random().ints(n).forEach(i -> myList.add(RaceBikeFactory.newRandomRaceBike()));
        return myList;
    }

    public static void compareArrayListAndLinkedList(int n) {
        System.out.println("\n=== ARRAYLIST VS LINKEDLIST PERFORMANCE ===");
        //add at beginning
        long startAddA = System.currentTimeMillis();
        List<RaceBike> arrayList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            arrayList.add(0, RaceBikeFactory.newRandomRaceBike());
        }
        long endAddA = System.currentTimeMillis();

        long startAddL = System.currentTimeMillis();
        List<RaceBike> linkedList = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            linkedList.add(0, RaceBikeFactory.newRandomRaceBike());
        }
        long endAddL = System.currentTimeMillis();

        System.out.printf("Adding %d at beginning of ArrayList: %d ms.\n", n, endAddA - startAddA);
        System.out.printf("Adding %d at beginning of LinkedList: %d ms.\n", n, endAddL - startAddL);

        //get at end
        long startGetA = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            arrayList.get(n - 1);
        }
        long endGetA = System.currentTimeMillis();

        long startGetL = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            linkedList.get(n - 1);
        }
        long endGetL = System.currentTimeMillis();

        System.out.printf("Getting at end %d times from ArrayList: %d ms.\n", n, endGetA - startGetA);
        System.out.printf("Getting at end %d times from LinkedList: %d ms.\n", n, endGetL - startGetL);
    }

    public static void testSelectionSort() {
        System.out.println("\n=== SELECTION SORT COMPARISONS ===");
        System.out.println("n;compares");
        for (int n = 1000; n <= 20000; n += 1000) {
            List<RaceBike> myList = randomList(n);

            RaceBike.compareCounter = 0;
            Kollections.selectionSort(myList);

            System.out.println(n + ";" + RaceBike.compareCounter);
        }
    }

    public static void testMergeSort() {
        System.out.println("\n=== MERGE SORT COMPARISONS ===");
        System.out.println("n;compares");
        for (int n = 1000; n <= 20000; n += 1000) {
            List<RaceBike> myList = randomList(n);

            RaceBike.compareCounter = 0;
            Kollections.mergeSort(myList);

            System.out.println(n + ";" + RaceBike.compareCounter);
        }
    }

    public static void compareListMapToHashMap(int size) {
        Map<Object, Object> hashMap = fillMap(true, size);
        Map<Object, Object> listMap = fillMap(false, size);

        long startHash = System.nanoTime();
        for (int i = 0; i < size; i++) {
            RaceBike.equalsCounter = 0;
            hashMap.get("bike" + i);
        }
        long endHash = System.nanoTime();

        System.out.println("HashMap: n = " + size + ", equalscount = " + RaceBike.equalsCounter + ", nanosec = " + (endHash - startHash));

        long startList = System.nanoTime();
        for (int i = 0; i < size; i++) {
            RaceBike.equalsCounter = 0;
            listMap.get("bike" + i);
        }
        long endList = System.nanoTime();

        System.out.println("ListMap: n = " + size + ", equalscount = " + RaceBike.equalsCounter + ", nanosec = " + (endList - startList));
    }

    private static Map<Object, Object> fillMap(boolean useHashMap, int size) {
        if (useHashMap) {
            Map<Object, Object> map = new HashMap<>();
            for (int i = 0; i < size; i++) {
                RaceBike bike = RaceBikeFactory.newEmptyRaceBike();
                bike.setModel("bike" + i);
                map.put(bike.getModel(), "This is bike " + bike.getModel());
            }
            return map;
        };
        Map<Object, Object> map = new ListMap<>();
        for (int i = 0; i < size; i++) {
            RaceBike bike = RaceBikeFactory.newEmptyRaceBike();
            bike.setModel("bike" + i);
            map.put(bike.getModel(), "This is bike " + bike.getModel());
        }
        return map;
    }
}