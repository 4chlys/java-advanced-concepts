package be.kdg;

import be.kdg.model.BrakeType;
import be.kdg.model.FrameMaterial;
import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikeFactory;
import be.kdg.threading.RaceBikeAttacker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Demo10 {
    public static void main(String[] args) {
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
