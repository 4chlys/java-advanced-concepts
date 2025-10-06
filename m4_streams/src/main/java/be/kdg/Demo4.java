package be.kdg;

import be.kdg.data.Data;
import be.kdg.model.Brand;
import be.kdg.model.Color;
import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikes;

import java.math.BigDecimal;
import java.util.List;

public class Demo4 {

    public static void main(String[] args) {
        System.out.println("=== RACE BIKE COLLECTION DEMO ===\n");

        RaceBikes raceBikes = new RaceBikes();
        for (RaceBike raceBike : Data.getData()) {
            raceBikes.add(raceBike);
        }

        // 2.2 Maak een nieuwe main klasse Demo_4 en test de nieuwe methode drie keer uit met telkens een ander
        //sorteercriterium.

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Brand");
        for (RaceBike raceBike : raceBikes.sortedBy(RaceBike::getBrand)) {
            System.out.println(raceBike);
        }

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Model");
        for (RaceBike raceBike : raceBikes.sortedBy(RaceBike::getModel)) {
            System.out.println(raceBike);
        }

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Price");
        for (RaceBike raceBike : raceBikes.sortedBy(RaceBike::getPrice)){
            System.out.println(raceBike);
        }


        System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");
    }
}