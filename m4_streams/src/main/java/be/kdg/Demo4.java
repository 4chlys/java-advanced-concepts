package be.kdg;

import be.kdg.data.Data;
import be.kdg.model.*;
import be.kdg.util.RaceBikeFunctions;

import java.math.BigDecimal;
import java.util.List;

public class Demo4 {

    public static void main(String[] args) {
        System.out.println("=== RACE BIKE COLLECTION DEMO ===\n");

        RaceBikes raceBikes1 = new RaceBikes();
        for (RaceBike raceBike : Data.getData()) {
            raceBikes1.add(raceBike);
        }

        // 2.2 Maak een nieuwe main klasse Demo_4 en test de nieuwe methode drie keer uit met telkens een ander
        //sorteercriterium.

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Brand");
        for (RaceBike raceBike : raceBikes1.sortedBy(RaceBike::getBrand)) {
            System.out.println(raceBike);
        }

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Model");
        for (RaceBike raceBike : raceBikes1.sortedBy(RaceBike::getModel)) {
            System.out.println(raceBike);
        }

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Price");
        for (RaceBike raceBike : raceBikes1.sortedBy(RaceBike::getPrice)){
            System.out.println(raceBike);
        }

        // 2.4. In de klasse Demo_4:
        //a. Vraag aan de klasse Data een gevulde List met basisobjecten.
        //b. Roep drie keer de zopas geschreven filtermethode op en geef telkens een ander Predicate mee.
        //c. Geef het Predicate bij de oproep mee als een Lambda-expression.
        //d. Controleer de afdruk.
        RaceBikes raceBikes2 = new RaceBikes();
        for (RaceBike raceBike : Data.getData()) {
            raceBikes2.add(raceBike);
        }

        System.out.println("\n2.4 Testing RaceBikeFunctions FilteredList: Brand is Trek, Color is Green and FrameMaterial is Carbon");
        Brand filterBrand = Brand.TREK;
        Color filterColor = Color.GREEN;
        FrameMaterial filterFrameMaterial = FrameMaterial.CARBON;
        List<RaceBike> filteredBrand = RaceBikeFunctions.filteredList(raceBikes2.getAllRaceBikes(),
                bike -> bike.getBrand() == filterBrand && bike.getColor() == filterColor && bike.getFrameMaterial() == filterFrameMaterial);
        filteredBrand.forEach(System.out::println);

        //2.6. In de klasse Demo_4 ga je deze methode uittesten:
        //a. Vraag aan de klasse Data opnieuw een vers gevulde List met basisobjecten.
        //b. Roep twee keer de zopas geschreven methode average op en geef telkens een andere Function
        //mee, die bepaalt van welk attribuut het gemiddelde moet berekend worden.
        //c. Geef de Function bij de oproep mee als een Lambda-expression.
        RaceBikes raceBikes3 = new RaceBikes();
        for (RaceBike raceBike : Data.getData()) {
            raceBikes3.add(raceBike);
        }

        System.out.println("\n2.6 Testing RaceBikeFunctions Average: Average price for bikes and average weight of bikes");
        System.out.printf("Average price for all bikes: %.2f €\n",
                RaceBikeFunctions.average(
                        raceBikes3.getAllRaceBikes(),
                        r -> r.getPrice().doubleValue()
                )
        );

        System.out.printf("Average weight of all bikes: %.3f kg\n",
                RaceBikeFunctions.average(
                        raceBikes3.getAllRaceBikes(),
                        RaceBike::getWeight
                )
        );

        //2.8. In de klasse Demo_4 ga je deze methode uittesten:
        //a. b. Roep twee keer de zopas geschreven methode op en geef telkens een ander Predicate mee.
        //Geef het Predicate bij de oproep mee als een Lambda-expression.
        RaceBikes raceBikes4 = new RaceBikes();
        for (RaceBike raceBike : Data.getData()) {
            raceBikes4.add(raceBike);
        }

        System.out.println("\n2.8 Testing RaceBikeFunctions CountIf: ");
        System.out.printf(
                "Count of bikes with price less than 10.000: %d\n",
                RaceBikeFunctions.countIf(
                        raceBikes4.getAllRaceBikes(),
                        bike -> bike.getPrice().compareTo(BigDecimal.valueOf(10_000)) < 0
                )
        );
        System.out.printf(
                "Count of bikes with weight higher than 8kg: %d\n",
                RaceBikeFunctions.countIf(
                        raceBikes4.getAllRaceBikes(),
                        bike -> bike.getWeight() > 8
                )
        );


        System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");
    }
}