package be.kdg;

import be.kdg.model.*;
import be.kdg.threading.RaceBikeAttacker;
import be.kdg.threading.RaceBikeRunnable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Demo9 {
    private static final int TESTCOUNT = 100;

    public static void main(String[] args) {
        System.out.println("=== RaceBike Threading Demo ===");
        System.out.println("Running " + TESTCOUNT + " test iterations...\n");

        List<Long> executionTimes = new ArrayList<>();

        for (int iteration = 0; iteration < TESTCOUNT; iteration++) {
            long startTime = System.currentTimeMillis();

            RaceBikeRunnable carbonBikesTask = new RaceBikeRunnable(
                    bike -> bike.getFrameMaterial() == FrameMaterial.CARBON
            );
            Thread thread1 = new Thread(carbonBikesTask, "CarbonBikeThread-" + iteration);

            RaceBikeRunnable cheapBikesTask = new RaceBikeRunnable(
                    bike -> bike.getPrice() != null &&
                            bike.getPrice().compareTo(new BigDecimal("5000")) < 0
            );
            Thread thread2 = new Thread(cheapBikesTask, "CheapBikeThread-" + iteration);

            RaceBikeRunnable discBrakesTask = new RaceBikeRunnable(
                    bike -> bike.getBrakeType() == BrakeType.DISC_HYDRAULIC
            );
            Thread thread3 = new Thread(discBrakesTask, "DiscBrakeThread-" + iteration);

            thread1.start();
            thread2.start();
            thread3.start();

            try {
                thread1.join();
                thread2.join();
                thread3.join();

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                executionTimes.add(executionTime);

                if (iteration == 0) {
                    System.out.println("=== First Iteration Results ===\n");

                    System.out.println("Thread 1 - CARBON frame bikes (" +
                            carbonBikesTask.getFilteredCount() + " found):");
                    carbonBikesTask.getFilteredBikes().stream()
                            .limit(5)
                            .forEach(System.out::println);

                    System.out.println("\nThread 2 - Bikes under 5000 EUR (" +
                            cheapBikesTask.getFilteredCount() + " found):");
                    cheapBikesTask.getFilteredBikes().stream()
                            .limit(5)
                            .forEach(System.out::println);

                    System.out.println("\nThread 3 - Hydraulic disc brake bikes (" +
                            discBrakesTask.getFilteredCount() + " found):");
                    discBrakesTask.getFilteredBikes().stream()
                            .limit(5)
                            .forEach(System.out::println);

                    System.out.println("\nExecution time: " + executionTime + " ms");
                    System.out.println("\n" + "=".repeat(50) + "\n");
                }

                if ((iteration + 1) % 10 == 0) {
                    System.out.println("Completed " + (iteration + 1) + "/" + TESTCOUNT + " iterations...");
                }

            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }

        double averageTime = executionTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("=== Final Statistics ===");
        System.out.println("Total iterations: " + TESTCOUNT);
        System.out.println("Average execution time: " + String.format("%.2f", averageTime) + " ms");

        long minTime = executionTimes.stream()
                .mapToLong(Long::longValue)
                .min()
                .orElse(0);

        long maxTime = executionTimes.stream()
                .mapToLong(Long::longValue)
                .max()
                .orElse(0);

        System.out.println("Minimum execution time: " + minTime + " ms");
        System.out.println("Maximum execution time: " + maxTime + " ms");
        System.out.println("=".repeat(50));

        System.out.println("=== RaceBike Attacker Demo ===\n");

        System.out.println("--- TEST 1: WITHOUT Synchronization (will fail) ---\n");
        //runTest(false);

        //System.out.println("\n" + "=".repeat(60) + "\n");

        System.out.println("--- TEST 2: WITH Synchronization (will succeed) ---\n");
        runTest(true);
    }

    private static void runTest(boolean useSynchronization) {
        List<RaceBike> bikes = Stream.generate(RaceBikeFactory::newRandomRaceBike)
                .limit(1000)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        Object lock = new Object();

        RaceBikeAttacker attacker1, attacker2, attacker3;

        if (useSynchronization) {
            attacker1 = new RaceBikeAttacker(bikes,
                    bike -> bike.getFrameMaterial() == FrameMaterial.CARBON, lock);
            attacker2 = new RaceBikeAttacker(bikes,
                    bike -> bike.getPrice() != null &&
                            bike.getPrice().compareTo(new BigDecimal("5000")) < 0, lock);
            attacker3 = new RaceBikeAttacker(bikes,
                    bike -> bike.getBrakeType() == BrakeType.DISC_HYDRAULIC, lock);
        } else {
            attacker1 = new RaceBikeAttacker(bikes,
                    bike -> bike.getFrameMaterial() == FrameMaterial.CARBON);
            attacker2 = new RaceBikeAttacker(bikes,
                    bike -> bike.getPrice() != null &&
                            bike.getPrice().compareTo(new BigDecimal("5000")) < 0);
            attacker3 = new RaceBikeAttacker(bikes,
                    bike -> bike.getBrakeType() == BrakeType.DISC_HYDRAULIC);
        }

        Thread thread1 = new Thread(attacker1, "CarbonKiller");
        Thread thread2 = new Thread(attacker2, "CheapBikeKiller");
        Thread thread3 = new Thread(attacker3, "HydraulicKiller");

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();

            long remainingCarbonCount = bikes.stream()
                    .filter(bike -> bike.getFrameMaterial() == FrameMaterial.CARBON)
                    .count();

            long remainingCheapCount = bikes.stream()
                    .filter(bike -> bike.getPrice() != null &&
                            bike.getPrice().compareTo(new BigDecimal("5000")) < 0)
                    .count();

            long remainingHydraulicCount = bikes.stream()
                    .filter(bike -> bike.getBrakeType() == BrakeType.DISC_HYDRAULIC)
                    .count();

            System.out.println("\nAfter purge:");
            System.out.println("Number of CARBON frames: " + remainingCarbonCount);
            System.out.println("Number of cheap bikes (<5000 EUR): " + remainingCheapCount);
            System.out.println("Number of hydraulic disc brakes: " + remainingHydraulicCount);

        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.err.println("\nERROR: " + e.getClass().getSimpleName() + ": " + e.getMessage());
            System.out.println("✗ Concurrent modification detected!");
        }
    }
}