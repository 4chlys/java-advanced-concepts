package be.kdg;

import be.kdg.data.Data;
import be.kdg.model.*;
import be.kdg.util.RaceBikeFunctions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Demo4 {

    public static void main(String[] args) {
        System.out.println("=== RACE BIKE COLLECTION DEMO ===\n");

        RaceBikes raceBikes1 = new RaceBikes(Data.getData());

        // 2.2 Maak een nieuwe main klasse Demo_4 en test de nieuwe methode drie keer uit met telkens een ander
        //sorteercriterium.

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Brand");
        raceBikes1.sortedBy(RaceBike::getBrand).forEach(System.out::println);

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Model");
        raceBikes1.sortedBy(RaceBike::getModel).forEach(System.out::println);

        System.out.println("\n2.2 Testing RaceBikes SortedBy: Sorted by Price");
        raceBikes1.sortedBy(RaceBike::getPrice).forEach(System.out::println);

        // 2.4. In de klasse Demo_4:
        //a. Vraag aan de klasse Data een gevulde List met basisobjecten.
        //b. Roep drie keer de zopas geschreven filtermethode op en geef telkens een ander Predicate mee.
        //c. Geef het Predicate bij de oproep mee als een Lambda-expression.
        //d. Controleer de afdruk.
        RaceBikes raceBikes2 = new RaceBikes(Data.getData());

        System.out.println("\n2.4 Testing RaceBikeFunctions FilteredList: Brand is Trek, Color is Green and FrameMaterial is Carbon");
        Brand filterBrand = Brand.TREK;
        Color filterColor = Color.GREEN;
        FrameMaterial filterFrameMaterial = FrameMaterial.CARBON;
        List<RaceBike> filteredBrand = RaceBikeFunctions.filteredList(raceBikes2.getAllRaceBikes(),
                bike -> bike.getBrand() == filterBrand && bike.getColor() == filterColor && bike.getFrameMaterial() == filterFrameMaterial);
        filteredBrand.forEach(System.out::println);

        System.out.println("\nFiltered bikes with price less than 5000:");
        List<RaceBike> filteredPrice = RaceBikeFunctions.filteredList(raceBikes2.getAllRaceBikes(),
                bike -> bike.getPrice().compareTo(BigDecimal.valueOf(5000)) < 0);
        filteredPrice.forEach(System.out::println);

        System.out.println("\nFiltered bikes with weight less than 8kg:");
        List<RaceBike> filteredWeight = RaceBikeFunctions.filteredList(raceBikes2.getAllRaceBikes(),
                bike -> bike.getWeight() < 8.0);
        filteredWeight.forEach(System.out::println);

        //2.6. In de klasse Demo_4 ga je deze methode uittesten:
        //a. Vraag aan de klasse Data opnieuw een vers gevulde List met basisobjecten.
        //b. Roep twee keer de zopas geschreven methode average op en geef telkens een andere Function
        //mee, die bepaalt van welk attribuut het gemiddelde moet berekend worden.
        //c. Geef de Function bij de oproep mee als een Lambda-expression.
        RaceBikes raceBikes3 = new RaceBikes(Data.getData());

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
        RaceBikes raceBikes4 = new RaceBikes(Data.getData());

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

        System.out.println("-".repeat(30));

        //In de klasse Demo_4 vraag je opnieuw een verse List van basisobjecten op aan de klasse Data.
        //Je gaat nu via streaming de onderstaande opdrachten uitvoeren, toegepast op jouw dataList.
        //Programmeer alles zo compact en efficiënt mogelijk!
        //1.1. Tel hoeveel elementen aan een bepaald datum-criterium beantwoorden.
        RaceBikes raceBikes5 = new RaceBikes(Data.getData());
        System.out.println("\n3.1.1 Counting data stream");
        System.out.printf(
                "Amount of bikes released before 01/01/2021: %d",
                raceBikes5.getAllRaceBikes().stream()
                        .filter(r -> r.getReleaseDate().isBefore(LocalDate.of(2021,1,1)))
                        .count()
                );

        //1.2. Sorteer (op 2 attributen!) en druk af
        System.out.println("\n3.1.2 Sorting data stream");
        System.out.println("Sorted race bikes by brand and framesize:");

        raceBikes5.getAllRaceBikes().stream()
                .sorted(Comparator.comparing(RaceBike::getBrand).thenComparing(RaceBike::getFrameSize))
                .forEach(System.out::println);

        //1.3 Zet een attribuut om naar hoofdletters, verwijder de dubbels, sorteer in aflopende volgorde en zet de
        //elementen in één string, gescheiden door komma's.
        System.out.println("\n3.1.3 Converting to uppercase, removing duplicates, sorting in descending order and joining with commas");
        String brands = raceBikes5.getAllRaceBikes().stream()
                .map(r -> r.getBrand().name())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.joining(", "));
        System.out.println("All race bike brands: " + brands);

        //1.4 Filter op een bepaald criterium en pik er eentje uit met findAny
        System.out.println("\n3.1.4 Filtering race bikes by price higher than 10.000 and picking one at random");
        raceBikes5.getAllRaceBikes().stream()
                .filter(r -> r.getPrice().compareTo(BigDecimal.valueOf(10_000)) > 0)
                .findAny().ifPresent(System.out::println);

        //1.5 Toon twee kampioenen in een specifiek gebied
        System.out.println("\n3.1.5 Showing two race bikes with the highest properties");

        raceBikes5.getAllRaceBikes().stream()
                .max(Comparator.comparing(RaceBike::getPrice))
                .ifPresent(bike -> System.out.println("Highest price: " + bike));

        raceBikes5.getAllRaceBikes().stream()
                .max(Comparator.comparing(RaceBike::getWeight))
                .ifPresent(bike -> System.out.println("Heaviest bike: " + bike));

        //1.6 Filter op een String-criterium, map elk element op een String (bijvoorbeeld op naam), sorteer alfabetisch
        //en collect als een List van String.
        System.out.println("\n3.1.6 Filtering race bikes by model starting with a 'C' and sorting alphabetically");
        System.out.println(raceBikes5.getAllRaceBikes().stream()
                .map(RaceBike::getModel)
                .filter(model -> model.startsWith("C"))
                .sorted()
                .toList());

        //1.7 Sorteer op een datum-attribuut en partitioneer in twee groepen (voor/na die datum).
        LocalDate filterDate = LocalDate.of(2021,1,1);

        System.out.println("\n3.1.7 Sorting race bikes by release date and partitioning into two groups");
        System.out.println("Race bikes released before " + filterDate + ":");
        raceBikes5.getAllRaceBikes().stream()
                .filter(r -> r.getReleaseDate().isBefore(filterDate))
                .forEach(System.out::println);
        System.out.println("Race bikes released after " + filterDate + ":");
        raceBikes5.getAllRaceBikes().stream()
                .filter(r -> r.getReleaseDate().isAfter(filterDate))
                .forEach(System.out::println);

        //1.8 Tenslotte de kers op de taart: groepeer alle objecten in bepaalde categorieën en sorteer de namen
        //alfabetisch binnen elke categorie.
        System.out.println("\n3.1.8 Grouping race bikes by frame material and sorting by model");

        Arrays.stream(FrameMaterial.values())
                .forEach(fm -> {
                    String models = raceBikes5.getAllRaceBikes().stream()
                            .filter(r -> r.getFrameMaterial() == fm)
                            .map(RaceBike::getModel)
                            .sorted()
                            .collect(Collectors.joining(", "));

                    System.out.printf("%-11s: %s%n", fm, models);
                });


        System.out.println("\n=== DEMO COMPLETED SUCCESSFULLY ===");
    }
}