package be.kdg;

import be.kdg.kollections.lists.ArrayList;
import be.kdg.kollections.Kollections;
import be.kdg.kollections.lists.LinkedList;
import be.kdg.kollections.lists.List;
import be.kdg.kollections.maps.HashMap;
import be.kdg.kollections.maps.ListMap;
import be.kdg.kollections.maps.Map;
import be.kdg.kollections.sets.ArraySet;
import be.kdg.kollections.sets.Set;
import be.kdg.kollections.sets.TreeSet;
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
        for (int n = 1000; n <= 2000; n += 1000) {
            List<RaceBike> myList = randomList(n);

            RaceBike.compareCounter = 0;
            Kollections.selectionSort(myList);

            System.out.println(n + ";" + RaceBike.compareCounter);
        }
    }

    public static void testMergeSort() {
        System.out.println("\n=== MERGE SORT COMPARISONS ===");
        System.out.println("n;compares");
        for (int n = 1000; n <= 2000; n += 1000) {
            List<RaceBike> myList = randomList(n);

            RaceBike.compareCounter = 0;
            Kollections.mergeSort(myList);

            System.out.println(n + ";" + RaceBike.compareCounter);
        }
    }

    public static void compareListMapToHashMap(int size) {
        System.out.println("\n=== HASHMAP VS LISTMAP PERFORMANCE ===");

        List<RaceBike> bikeKeys = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            bikeKeys.add(RaceBikeFactory.newRandomRaceBike());
        }

        Map<RaceBike, String> hashMap = fillMap(true, bikeKeys);

        RaceBike.equalsCounter = 0;
        RaceBike.compareCounter = 0;
        long startHash = System.nanoTime();

        for (int i = 0; i < size; i++) {
            hashMap.get(bikeKeys.get(i));
        }

        long endHash = System.nanoTime();

        System.out.println("HashMap: n = " + size + ", equalscount = " + RaceBike.equalsCounter + ", comparecount = " + RaceBike.compareCounter + ", nanosec = " + (endHash - startHash));

        Map<RaceBike, String> listMap = fillMap(false, bikeKeys);

        RaceBike.equalsCounter = 0;
        RaceBike.compareCounter = 0;
        long startList = System.nanoTime();

        for (int i = 0; i < size; i++) {
            listMap.get(bikeKeys.get(i));
        }

        long endList = System.nanoTime();

        System.out.println("ListMap: n = " + size + ", equalscount = " + RaceBike.equalsCounter + ", comparecount = " + RaceBike.compareCounter + ", nanosec = " + (endList - startList));
    }

    private static Map<RaceBike, String> fillMap(boolean useHashMap, List<RaceBike> bikeKeys) {
        Map<RaceBike, String> map = useHashMap ? new HashMap<>() : new ListMap<>();

        for (int i = 0; i < bikeKeys.size(); i++) {
            RaceBike bike = bikeKeys.get(i);
            map.put(bike, "Bike model: " + bike.getModel());
        }

        return map;
    }

    public static void compareArraySetToTreeSet(int size) {
        System.out.println("\n=== ARRAYSET VS TREESET PERFORMANCE ===");

        Set<RaceBike> arraySet = new ArraySet<>();

        RaceBike.equalsCounter = 0;
        RaceBike.compareCounter = 0;

        long startArraySet = System.nanoTime();
        for (int i = 0; i < size; i++) {
            arraySet.add(RaceBikeFactory.newRandomRaceBike());
        }
        long endArraySet = System.nanoTime();

        System.out.println("ArraySet, n = " + size + ": equalscount: " + RaceBike.equalsCounter);
        System.out.println("ArraySet, n = " + size + ": comparecount: " + RaceBike.compareCounter);
        System.out.println("ArraySet, n = " + size + ": nanosec: " + (endArraySet - startArraySet));

        Set<RaceBike> treeSet = new TreeSet<>();

        RaceBike.equalsCounter = 0;
        RaceBike.compareCounter = 0;

        long startTreeSet = System.nanoTime();
        for (int i = 0; i < size; i++) {
            treeSet.add(RaceBikeFactory.newRandomRaceBike());
        }
        long endTreeSet = System.nanoTime();

        System.out.println("TreeSet, n = " + size + ": equalscount: " + RaceBike.equalsCounter);
        System.out.println("TreeSet, n = " + size + ": comparecount: " + RaceBike.compareCounter);
        System.out.println("TreeSet, n = " + size + ": nanosec: " + (endTreeSet - startTreeSet));
    }
}