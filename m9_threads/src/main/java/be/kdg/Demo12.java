package be.kdg;

import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikeFactory;
import be.kdg.model.RaceBikes;

public class Demo12 {

    public static void main(String[] args) {
        System.out.println("=== RaceBikes Thread-Safety Demo ===\n");

        RaceBikes raceBikes = new RaceBikes();

        Runnable addBikesTask = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " started adding bikes...");

            for (int i = 0; i < 5000; i++) {
                RaceBike bike = RaceBikeFactory.newRandomRaceBike();
                raceBikes.add(bike);
            }

            System.out.println(threadName + " finished adding bikes.");
        };

        Thread thread1 = new Thread(addBikesTask, "BikeAdder-1");
        Thread thread2 = new Thread(addBikesTask, "BikeAdder-2");

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();

            int finalSize = raceBikes.getSize();
            System.out.println("\nAfter adding by 2 threads with 5000 objects each:");
            System.out.println("raceBikes size = " + finalSize);

            if (finalSize == 10000) {
                System.out.println("✓ SUCCESS: All bikes added correctly!");
            } else {
                System.out.println("✗ PROBLEM: Expected 10000, got " + finalSize);
                System.out.println("This is because TreeSet is NOT thread-safe!");
            }

        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}