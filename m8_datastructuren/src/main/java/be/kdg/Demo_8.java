package be.kdg;

import be.kdg.kollections.lists.List;
import be.kdg.model.*;
import be.kdg.kollections.*;
import java.util.stream.Stream;

public class Demo_8 {
    public static void main(String[] args) {
        System.out.println("=== RACE BIKE FACTORY DEMO ===\n");
        RaceBike emptyBike = RaceBikeFactory.newEmptyRaceBike();
        System.out.println("Empty RaceBike:");
        System.out.println(emptyBike);
        System.out.println();

        RaceBike filledBike = RaceBikeFactory.newFilledRaceBike(
                be.kdg.model.Brand.TREK,
                "Domane SL7",
                be.kdg.model.Color.RED,
                8.5,
                54,
                java.time.LocalDate.of(2022, 5, 1),
                java.math.BigDecimal.valueOf(5999),
                Currency.EUR,
                Groupset.SRAM_RIVAL_ETAP_AXS,
                FrameMaterial.CARBON,
                ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC,
                WheelSize.ROAD_700C
        );
        System.out.println("Filled RaceBike:");
        System.out.println(filledBike);
        System.out.println();

        System.out.println("30 random RaceBikes sorted by brand + model:");
        Stream.generate(RaceBikeFactory::newRandomRaceBike)
                .limit(30)
                .sorted()
                .forEach(System.out::println);

        System.out.println("\n=== KOLLECTIONS DEMO ===\n");
        List<RaceBike> test = PerformanceTester.randomList(30);

        // 4.6 Test Selection Sort
        System.out.println("--- Testing Selection Sort (30 items) ---");
        Kollections.selectionSort(test);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        // 4.6 Test Merge Sort
        System.out.println("\n--- Testing Merge Sort (30 items) ---");
        test = PerformanceTester.randomList(30);
        Kollections.mergeSort(test);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        // 4.9 Test Quick Sort
        System.out.println("\n--- Testing Quick Sort (30 items) ---");
        test = PerformanceTester.randomList(30);
        Kollections.quickSort(test);
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i));
        }

        // 4.8 Run Performance Tests (Copy output to Excel)
        PerformanceTester.testSelectionSort();
        PerformanceTester.testMergeSort();

        // 4.11 Test Linear vs Binary Search
        System.out.println("\n--- Testing Search ---");
        List<RaceBike> searchList = PerformanceTester.randomList(30);
        Kollections.mergeSort(searchList);

        RaceBike existingElement = searchList.get(7);
        RaceBike nonExistingElement = RaceBikeFactory.newEmptyRaceBike();

        System.out.println("Searching for existing: " + existingElement);
        System.out.println("Linear Index: " + Kollections.lineairSearch(searchList, existingElement));
        System.out.println("Binary Index: " + Kollections.binarySearch(searchList, existingElement));

        System.out.println("\nSearching for non-existing bike...");
        System.out.println("Linear Index: " + Kollections.lineairSearch(searchList, nonExistingElement));
        System.out.println("Binary Index: " + Kollections.binarySearch(searchList, nonExistingElement));
    }
}