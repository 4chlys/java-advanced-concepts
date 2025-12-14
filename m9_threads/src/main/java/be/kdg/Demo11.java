package be.kdg;

import be.kdg.model.*;
import be.kdg.threading.RaceBikeCallable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Demo11 {
    private static final int TESTCOUNT = 100;

    public static void main(String[] args) {
        System.out.println("=== RaceBike Callable with ThreadPool Demo ===\n");

        List<Long> executionTimes = new ArrayList<>();

        for (int iteration = 0; iteration < TESTCOUNT; iteration++) {
            long startTime = System.currentTimeMillis();

            RaceBikeCallable callable1 = new RaceBikeCallable(
                    bike -> bike.getFrameMaterial() == FrameMaterial.CARBON
            );
            RaceBikeCallable callable2 = new RaceBikeCallable(
                    bike -> bike.getPrice() != null &&
                            bike.getPrice().compareTo(new BigDecimal("5000")) < 0
            );
            RaceBikeCallable callable3 = new RaceBikeCallable(
                    bike -> bike.getBrakeType() == BrakeType.DISC_HYDRAULIC
            );

            ExecutorService executor = Executors.newFixedThreadPool(3);

            try {
                Future<List<RaceBike>> future1 = executor.submit(callable1);
                Future<List<RaceBike>> future2 = executor.submit(callable2);
                Future<List<RaceBike>> future3 = executor.submit(callable3);

                List<RaceBike> carbonBikes = future1.get();
                List<RaceBike> cheapBikes = future2.get();
                List<RaceBike> hydraulicBikes = future3.get();

                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;
                executionTimes.add(executionTime);

                if (iteration == 0) {
                    System.out.println("=== First Iteration Results ===\n");
                    System.out.println("CARBON frame bikes generated: " + carbonBikes.size());
                    System.out.println("Cheap bikes (<5000 EUR) generated: " + cheapBikes.size());
                    System.out.println("Hydraulic disc brake bikes generated: " + hydraulicBikes.size());
                    System.out.println("\nFirst 5 CARBON bikes:");
                    carbonBikes.stream()
                            .limit(5)
                            .forEach(System.out::println);
                    System.out.println("\nExecution time: " + executionTime + " ms");
                    System.out.println("\n" + "=".repeat(50) + "\n");
                }

                if ((iteration + 1) % 10 == 0) {
                    System.out.println("Completed " + (iteration + 1) + "/" + TESTCOUNT + " iterations...");
                }

            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error during execution: " + e.getMessage());
                Thread.currentThread().interrupt();
            } finally {
                executor.shutdown();
                try {
                    if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                        executor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    executor.shutdownNow();
                    Thread.currentThread().interrupt();
                }
            }
        }

        double averageTime = executionTimes.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("3 Futures each collect 1000 race bikes (average of " +
                TESTCOUNT + " runs): " + String.format("%.1f", averageTime) + " ms");
        System.out.println("=".repeat(60));
    }
}