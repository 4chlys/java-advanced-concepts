package be.kdg;

import be.kdg.data.Data;
import be.kdg.generics.PriorityQueue;
import be.kdg.model.Brand;
import be.kdg.model.RaceBike;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Demo2 {
    public static void main(String[] args) {
//        PriorityQueue<String> myQueue = new PriorityQueue<>();
//        myQueue.enqueue("Tokio", 2);
//        myQueue.enqueue("Denver", 5);
//        myQueue.enqueue("Rio", 2);
//        myQueue.enqueue("Oslo", 3);
//        System.out.println("Overzicht van de PriorityQueue:");
//        System.out.println(myQueue.toString());
//        System.out.println("aantal: " + myQueue.getSize());
//        System.out.println("positie van Tokio: " + myQueue.search("Tokio"));
//        System.out.println("positie van Nairobi: " + myQueue.search("Nairobi"));
//        for (int i = 0; i < 4; i++) {
//            System.out.println("Dequeue: " + myQueue.dequeue());
//        }
//        System.out.println("Size na dequeue: " + myQueue.getSize());

        System.out.println("=== PRIORITY QUEUE TESTING WITH RACE BIKES ===");

        var raceBikes = Data.getData();
        var raceBikeQueue = new PriorityQueue<RaceBike>();
        Random random = new Random(42); // Fixed seed for consistent results

        // Test 1: Enqueue all race bikes with random priorities
        System.out.println("1. ENQUEUE TESTING");
        System.out.println("-".repeat(30));
        System.out.println("Adding race bikes with random priorities (1-5):");

        for (RaceBike raceBike : raceBikes) {
            int priority = random.nextInt(5) + 1;
            boolean added = raceBikeQueue.enqueue(raceBike, priority);
            System.out.printf("Priority %d: %-12s %-20s -> %s%n",
                    priority, raceBike.getBrand(), raceBike.getModel(),
                    added ? "Added" : "Duplicate");
        }

        System.out.println("\nTotal bikes in queue: " + raceBikeQueue.getSize());
        System.out.println("Is queue empty? " + raceBikeQueue.isEmpty());

        // Test 2: Search functionality
        System.out.println("\n2. SEARCH TESTING");
        System.out.println("-".repeat(30));

        // Search for existing bikes
        RaceBike trekBike = new RaceBike(Brand.TREK, "Emonda SL 6");
        System.out.println("Search Trek Emonda SL 6: position " + raceBikeQueue.search(trekBike));

        RaceBike specializedBike = new RaceBike(Brand.SPECIALIZED, "Tarmac SL7");
        System.out.println("Search Specialized Tarmac SL7: position " + raceBikeQueue.search(specializedBike));

        RaceBike giantBike = new RaceBike(Brand.GIANT, "TCR Advanced");
        System.out.println("Search Giant TCR Advanced: position " + raceBikeQueue.search(giantBike));

        // Search for non-existing bike
        RaceBike nonExistentBike = new RaceBike(Brand.BIANCHI, "NonExistent Model");
        System.out.println("Search non-existent Bianchi: position " + raceBikeQueue.search(nonExistentBike));

        // Test 3: Contains functionality
        System.out.println("\n3. CONTAINS TESTING");
        System.out.println("-".repeat(30));
        System.out.println("Contains Trek Emonda SL 6? " + raceBikeQueue.containsElement(trekBike));
        System.out.println("Contains Specialized Tarmac SL7? " + raceBikeQueue.containsElement(specializedBike));
        System.out.println("Contains non-existent bike? " + raceBikeQueue.containsElement(nonExistentBike));

        // Test 4: Duplicate handling
        System.out.println("\n4. DUPLICATE HANDLING TESTING");
        System.out.println("-".repeat(30));
        RaceBike existingBike = raceBikes.getFirst();
        System.out.printf("Trying to add existing bike again (%s %s): %s%n",
                existingBike.getBrand(), existingBike.getModel(),
                raceBikeQueue.enqueue(existingBike, 3) ? "Added" : "Rejected (duplicate)");

        // Add a completely new bike
        RaceBike newBike = new RaceBike(Brand.CERVELO, "Test Model");
        System.out.printf("Adding new bike (%s %s): %s%n",
                newBike.getBrand(), newBike.getModel(),
                raceBikeQueue.enqueue(newBike, 4) ? "Added" : "Rejected");

        System.out.println("Queue size after duplicate tests: " + raceBikeQueue.getSize());

        // Test 5: Display current queue state
        System.out.println("\n5. QUEUE STATE OVERVIEW");
        System.out.println("-".repeat(30));
        System.out.println("Current queue contents (by priority):");
        System.out.println(raceBikeQueue.toString());

        // Test 6: Dequeue testing (priority order)
        System.out.println("\n6. DEQUEUE TESTING (Priority Order)");
        System.out.println("-".repeat(30));
        System.out.println("Dequeuing first 10 bikes (should be in priority order):");

        for (int i = 1; i <= 10 && !raceBikeQueue.isEmpty(); i++) {
            RaceBike dequeuedBike = raceBikeQueue.dequeue();
            if (dequeuedBike != null) {
                System.out.printf("%2d. %-12s %-20s%n",
                        i, dequeuedBike.getBrand(), dequeuedBike.getModel());
            }
        }

        System.out.println("Queue size after dequeuing 10: " + raceBikeQueue.getSize());

        // Test 7: Search after modifications
        System.out.println("\n7. SEARCH AFTER MODIFICATIONS");
        System.out.println("-".repeat(30));
        System.out.println("Search positions after some bikes were dequeued:");
        System.out.println("Trek Emonda SL 6: position " + raceBikeQueue.search(trekBike));
        System.out.println("New Cervelo Test Model: position " + raceBikeQueue.search(newBike));

        // Test 8: Error handling
        System.out.println("\n8. ERROR HANDLING TESTING");
        System.out.println("-".repeat(30));

        // Test invalid priorities
        try {
            raceBikeQueue.enqueue(new RaceBike(Brand.BMC, "Test"), 0);
            System.out.println("ERROR: Should have thrown exception for priority 0");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for priority 0: " + e.getMessage());
        }

        try {
            raceBikeQueue.enqueue(new RaceBike(Brand.SCOTT, "Test"), 6);
            System.out.println("ERROR: Should have thrown exception for priority 6");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for priority 6: " + e.getMessage());
        }

        // Test 9: Empty queue behavior
        System.out.println("\n9. EMPTY QUEUE TESTING");
        System.out.println("-".repeat(30));

        // Dequeue all remaining items
        int remainingCount = raceBikeQueue.getSize();
        System.out.println("Dequeuing all remaining " + remainingCount + " bikes...");

        while (!raceBikeQueue.isEmpty()) {
            raceBikeQueue.dequeue();
        }

        System.out.println("Queue is now empty. Size: " + raceBikeQueue.getSize());
        System.out.println("Is empty? " + raceBikeQueue.isEmpty());
        System.out.println("Dequeue from empty queue: " + raceBikeQueue.dequeue());
        System.out.println("Search in empty queue: " + raceBikeQueue.search(trekBike));

        // Test 10: Final functionality test
        System.out.println("\n10. FINAL COMPREHENSIVE TEST");
        System.out.println("-".repeat(30));

        // Add a few bikes with specific priorities to test final ordering
        PriorityQueue<RaceBike> finalQueue = new PriorityQueue<>();

        finalQueue.enqueue(new RaceBike(Brand.TREK, "Priority 1 Test"), 1);
        finalQueue.enqueue(new RaceBike(Brand.SPECIALIZED, "Priority 5 Test"), 5);
        finalQueue.enqueue(new RaceBike(Brand.GIANT, "Priority 3 Test"), 3);
        finalQueue.enqueue(new RaceBike(Brand.CANYON, "Priority 5 Test 2"), 5);  // Same priority as Specialized
        finalQueue.enqueue(new RaceBike(Brand.BMC, "Priority 3 Test 2"), 3);     // Same priority as Giant

        System.out.println("Final test queue contents:");
        System.out.println(finalQueue.toString());

        System.out.println("\nFinal dequeue order (should be: Priority 5 bikes first, then 3, then 1):");
        while (!finalQueue.isEmpty()) {
            RaceBike bike = finalQueue.dequeue();
            System.out.println("-> " + bike.getBrand() + " " + bike.getModel());
        }

        System.out.println("=== ALL PRIORITY QUEUE TESTS COMPLETED! ===");
    }
}