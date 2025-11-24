package be.kdg;

import be.kdg.kollections.ArrayList;
import be.kdg.kollections.Kollections;
import be.kdg.kollections.LinkedList;
import be.kdg.kollections.List;
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
}