package be.kdg;

import be.kdg.model.*;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.LogManager;

public class Demo5 {
    public static void main(String[] args) {
        // 3.6 Vanuit de main in Demo_5 zorg je ervoor dat het
        //configuratiebestand logging.properties geladen wordt.
        try {
            InputStream inputStream = Demo5.class.getResourceAsStream("/logging.properties");
            if (inputStream != null) {
                LogManager.getLogManager().readConfiguration(inputStream);
                System.out.println("Logging configuration loaded successfully\n");
            } else {
                System.err.println("Could not find logging.properties");
            }
        } catch (Exception e) {
            System.err.println("Error loading logging configuration: " + e.getMessage());
        }

        System.out.println("=== TESTING LOGGING WITH INVALID DATA ===\n");

        // 3.2 Maak een nieuwe klasse Demo_5 met een main. Maak daar een aantal foutieve basisobjecten aan om de
        //logging te testen.
        System.out.println("Creating bikes with invalid data...\n");

        RaceBike bike1 = new RaceBike();
        bike1.setBrand(null);
        bike1.setModel("");
        bike1.setWeight(-5.0);
        bike1.setFrameSize(-10);
        bike1.setReleaseDate(LocalDate.of(2078, 12, 18));
        bike1.setPrice(BigDecimal.valueOf(-1000));

        RaceBike bike2 = new RaceBike(Brand.CERVELO, "R5");
        bike2.setModel(null);
        bike2.setWeight(-7.5);

        RaceBike bike3 = new RaceBike(Brand.TREK, "Emonda");
        bike3.setReleaseDate(LocalDate.of(2030, 1, 1));
        bike3.setFrameSize(-50);

        System.out.println("\n=== TESTING LOGGING WITH ADD/REMOVE ===\n");

        // 3.6 Test nu in de main het toevoegen en verwijderen in de multiklasse uit.
        //Voer de code meerdere keren uit (best runnen vanuit gradle anders vindt hij het juiste pad niet) en
        //bekijk de aangemaakte logfiles; dat zouden er max 4 mogen zijn.
        //Controleer de inhoud van de logfiles: zowel de FINER als de SEVERE meldingen moeten hierin gelogd
        //worden.
        RaceBikes collection = new RaceBikes();

        RaceBike validBike1 = new RaceBike(Brand.CERVELO, "R5", Color.BLACK, 7.2, 56,
                LocalDate.of(2023, 1, 15), BigDecimal.valueOf(12000), Currency.EUR,
                Groupset.SHIMANO_DURA_ACE_Di2, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        RaceBike validBike2 = new RaceBike(Brand.COLNAGO, "V4Rs", Color.RED, 8.1, 54,
                LocalDate.of(2022, 6, 10), BigDecimal.valueOf(9500), Currency.EUR,
                Groupset.SRAM_RED_ETAP_AXS, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        RaceBike validBike3 = new RaceBike(Brand.BIANCHI, "Oltre XR4", Color.GREEN, 7.8, 52,
                LocalDate.of(2021, 3, 20), BigDecimal.valueOf(11000), Currency.EUR,
                Groupset.CAMPAGNOLO_SUPER_RECORD_EPS, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        RaceBike validBike4 = new RaceBike(Brand.TREK, "Emonda SLR", Color.BLUE, 6.8, 58,
                LocalDate.of(2023, 9, 5), BigDecimal.valueOf(13500), Currency.USD,
                Groupset.SHIMANO_DURA_ACE_MECHANICAL, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.RIM_CALIPER, WheelSize.ROAD_700C);

        System.out.println("Adding bikes to collection...");
        collection.add(validBike1);
        collection.add(validBike2);
        collection.add(validBike3);
        collection.add(validBike4);

        System.out.println("\nRemoving bikes from collection...");
        collection.remove(validBike2);
        collection.remove(Brand.BIANCHI, "Oltre XR4");

        System.out.println("\nCurrent collection size: " + collection.getSize());

        System.out.println("\n=== DEMO COMPLETED ===");
        System.out.println("Check the log directory for log files with SEVERE and FINER messages");
    }
}