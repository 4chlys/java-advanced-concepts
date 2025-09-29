package be.kdg;

import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikes;
import be.kdg.model.Vehicle;
import be.kdg.reflection.ReflectionTools;

public class Demo3 {
    public static void main(String[] args) {
        System.out.println("=== REFLECTION DEMO ===");

        ReflectionTools.classAnalysis(Vehicle.class, RaceBike.class, RaceBikes.class);

        System.out.println("\n=== DEMO COMPLETED ===");
    }
}