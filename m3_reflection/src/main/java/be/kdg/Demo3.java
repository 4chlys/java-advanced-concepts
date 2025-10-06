package be.kdg;

import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikes;
import be.kdg.model.Vehicle;
import be.kdg.reflection.ReflectionTools;

public class Demo3 {
    public static void main(String[] args) {
        System.out.println("=== REFLECTION DEMO ===");

        // Call classAnalysis with 3 classes using varargs
        ReflectionTools.classAnalysis(Vehicle.class, RaceBike.class, RaceBikes.class);

        System.out.println("\n\n=== ANNOTATION DEMO ===");

        try {
            Object createdObject = ReflectionTools.runAnnotated(RaceBike.class);
            System.out.println("\nAangemaakt object door runAnnotated:");
            System.out.println(createdObject);
        } catch (Exception e) {
            System.err.println("Error running annotated methods: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== DEMO COMPLETED ===");
    }
}