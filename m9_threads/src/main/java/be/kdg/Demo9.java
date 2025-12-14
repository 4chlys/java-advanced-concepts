package be.kdg;

import be.kdg.model.*;
import be.kdg.threading.RaceBikeRunnable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    }
}