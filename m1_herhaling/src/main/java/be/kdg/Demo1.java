package be.kdg;

import be.kdg.data.Data;
import be.kdg.model.*;
import java.math.BigDecimal;
import java.util.List;

public class Demo1 {

    public static void main(String[] args) {
        System.out.println("=== RACE BIKE COLLECTION DEMO ===\n");

        // a. Maak een instantie van de multiklasse
        System.out.println("a. Creating RaceBikes collection instance...");
        RaceBikes collection = new RaceBikes();
        System.out.println("Collection created successfully.\n");

        // b. Vraag een gevulde datalist op en voeg al deze objecten toe aan de multiklasse
        System.out.println("b. Loading data and adding to collection...");
        List<RaceBike> dataList = Data.getData();
        System.out.println("Loaded " + dataList.size() + " race bikes from data source.");

        int addedCount = 0;
        for (RaceBike bike : dataList) {
            if (collection.add(bike)) {
                addedCount++;
            }
        }
        System.out.println("Successfully added " + addedCount + " unique race bikes to collection.");
        System.out.println("Collection size: " + collection.getSize() + "\n");

        // c. Voeg ook eens een dubbel object toe (dat zou niet mogen lukken)
        System.out.println("c. Testing duplicate prevention...");
        RaceBike duplicate = new RaceBike(Brand.SPECIALIZED, "S-Works Tarmac SL7", Color.BLUE);
        boolean duplicateAdded = collection.add(duplicate);
        System.out.println("Attempted to add duplicate (SPECIALIZED S-Works Tarmac SL7): " +
                (duplicateAdded ? "SUCCESS (ERROR!)" : "REJECTED (Correct!)"));
        System.out.println("Collection size after duplicate attempt: " + collection.getSize() + "\n");

        // d. Test de methoden search, remove en getSize uit
        System.out.println("d. Testing search, remove, and size methods...");

        System.out.println("\n--- Testing search functionality ---");
        RaceBike foundBike = collection.search(Brand.CANYON, "Ultimate CF SL 8.0");
        if (foundBike != null) {
            System.out.println("Found bike: " + foundBike);
        } else {
            System.out.println("Bike not found!");
        }

        boolean containsTest = collection.contains(Brand.PINARELLO, "Dogma F12");
        System.out.println("Contains PINARELLO Dogma F12: " + containsTest);

        System.out.println("\n--- Testing remove functionality ---");
        System.out.println("Size before removal: " + collection.getSize());
        RaceBike bikeToRemove = new RaceBike(Brand.CUBE, "Attain Race");
        boolean removed = collection.remove(bikeToRemove);
        System.out.println("Removed CUBE Attain Race: " + removed);
        System.out.println("Size after removal: " + collection.getSize());

        System.out.println("\n--- Testing size method ---");
        System.out.println("Final collection size: " + collection.getSize());
        System.out.println("Is collection empty: " + collection.isEmpty() + "\n");

        // e. Druk de 3 gesorteerde listen af
        System.out.println("e. Printing sorted collections...\n");

        System.out.println("--- ALL RACE BIKES (sorted by brand and model) ---");
        List<RaceBike> allBikes = collection.getAllRaceBikes();
        for (int i = 0; i < allBikes.size(); i++) {
            System.out.printf("%2d. %s%n", i + 1, allBikes.get(i));
        }

        System.out.println("\n--- BIKES SORTED BY BRAND ---");
        List<RaceBike> sortedByBrand = collection.sortedOnBrand();
        for (int i = 0; i < Math.min(10, sortedByBrand.size()); i++) {
            System.out.printf("%2d. %s%n", i + 1, sortedByBrand.get(i));
        }
        if (sortedByBrand.size() > 10) System.out.println("... (showing first 10 of " + sortedByBrand.size() + ")");

        System.out.println("\n--- BIKES SORTED BY MODEL ---");
        List<RaceBike> sortedByModel = collection.sortedOnModel();
        for (int i = 0; i < Math.min(10, sortedByModel.size()); i++) {
            System.out.printf("%2d. %s%n", i + 1, sortedByModel.get(i));
        }
        if (sortedByModel.size() > 10) System.out.println("... (showing first 10 of " + sortedByModel.size() + ")");

        System.out.println("\n--- BIKES SORTED BY PRICE ---");
        List<RaceBike> sortedByPrice = collection.sortedOnPrice();
        for (int i = 0; i < Math.min(10, sortedByPrice.size()); i++) {
            System.out.printf("%2d. %s%n", i + 1, sortedByPrice.get(i));
        }
        if (sortedByPrice.size() > 10) System.out.println("... (showing first 10 of " + sortedByPrice.size() + ")");

        // f. Test beide constructors uit en ook de IllegalArgumentException
        System.out.println("\nf. Testing constructors and exception handling...\n");

        System.out.println("--- Testing different constructors ---");
        RaceBike bike1 = new RaceBike();
        System.out.println("Default constructor: " + bike1);

        RaceBike bike2 = new RaceBike(Brand.BIANCHI, "Oltre XR4");
        System.out.println("Brand+Model constructor: " + bike2);

        RaceBike bike3 = new RaceBike(Brand.BMC, "TeamMachine SLR01", Color.RED);
        System.out.println("Brand+Model+Color constructor: " + bike3);

        System.out.println("\n--- Testing IllegalArgumentException handling ---");

        try {
            RaceBike invalidBrand = new RaceBike(null, "Test Model");
            System.out.println("ERROR: Should have thrown exception for null brand!");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for null brand: " + e.getMessage());
        }

        try {
            RaceBike invalidModel = new RaceBike(Brand.TREK, null);
            System.out.println("ERROR: Should have thrown exception for null model!");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for null model: " + e.getMessage());
        }

        try {
            RaceBike invalidModel2 = new RaceBike(Brand.TREK, "   ");
            System.out.println("ERROR: Should have thrown exception for empty model!");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for empty model: " + e.getMessage());
        }

        try {
            RaceBike bike = new RaceBike(Brand.GIANT, "Test Bike");
            bike.setWeight(-5.0);
            System.out.println("ERROR: Should have thrown exception for negative weight!");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for negative weight: " + e.getMessage());
        }

        try {
            RaceBike bike = new RaceBike(Brand.GIANT, "Test Bike");
            bike.setPrice(new BigDecimal("-1000"));
            System.out.println("ERROR: Should have thrown exception for negative price!");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for negative price: " + e.getMessage());
        }

        try {
            RaceBike bike = new RaceBike(Brand.GIANT, "Test Bike");
            bike.setReleaseDate(null);
            System.out.println("ERROR: Should have thrown exception for null release date!");
        } catch (IllegalArgumentException e) {
            System.out.println("OK: Correctly caught exception for null release date: " + e.getMessage());
        }

        System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");
    }
}