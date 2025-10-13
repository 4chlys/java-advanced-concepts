package be.kdg.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RaceBikesTest {
    // 2.2.a Voorzie vijf testobjecten van de basisklasse en één multiobject als private attributen.
    private RaceBike bike1;
    private RaceBike bike2;
    private RaceBike bike3;
    private RaceBike bike4;
    private RaceBike bike5;
    private RaceBikes raceBikes;

    @BeforeEach
    void setUp() {
        // 2.2.b Maak een @BeforeEach methode om de 5 basisobjecten te vullen met testgegevens en
        //vervolgens aan het multiobject toe te voegen.
        bike1 = new RaceBike(Brand.CERVELO, "R5", Color.BLACK, 7.2, 56,
                LocalDate.of(2023, 1, 15), BigDecimal.valueOf(12000), Currency.EUR,
                Groupset.SHIMANO_DURA_ACE_Di2, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        bike2 = new RaceBike(Brand.COLNAGO, "V4Rs", Color.RED, 8.1, 54,
                LocalDate.of(2022, 6, 10), BigDecimal.valueOf(9500), Currency.EUR,
                Groupset.SRAM_RED_ETAP_AXS, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        bike3 = new RaceBike(Brand.BIANCHI, "Oltre XR4", Color.GREEN, 7.8, 52,
                LocalDate.of(2021, 3, 20), BigDecimal.valueOf(11000), Currency.EUR,
                Groupset.CAMPAGNOLO_SUPER_RECORD_EPS, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        bike4 = new RaceBike(Brand.TREK, "Emonda SLR", Color.BLUE, 6.8, 58,
                LocalDate.of(2023, 9, 5), BigDecimal.valueOf(13500), Currency.USD,
                Groupset.SHIMANO_DURA_ACE_MECHANICAL, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.RIM_CALIPER, WheelSize.ROAD_700C);

        bike5 = new RaceBike(Brand.PINARELLO, "Dogma F", Color.YELLOW, 7.5, 56,
                LocalDate.of(2024, 2, 28), BigDecimal.valueOf(14000), Currency.EUR,
                Groupset.SHIMANO_ULTEGRA_Di2, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        raceBikes = new RaceBikes();
        raceBikes.add(bike1);
        raceBikes.add(bike2);
        raceBikes.add(bike3);
        raceBikes.add(bike4);
        raceBikes.add(bike5);
    }

    @Test
    void testAdd() {
        // 2.2.c Voorzie een methode om het toevoegen uit te testen. Check zeker ook op dubbels!
        RaceBikes testCollection = new RaceBikes();

        assertTrue(testCollection.add(bike1), "Adding new bike should return true");
        assertEquals(1, testCollection.getSize(), "Size should be 1 after adding first bike");

        assertFalse(testCollection.add(bike1), "Adding duplicate bike should return false");
        assertEquals(1, testCollection.getSize(), "Size should remain 1 after adding duplicate");
    }

    @Test
    void testRemove() {
        // 2.2.d Voorzie een methode om het verwijderen uit te proberen. Doe dat door voor en na de size te
        //checken. Test zeker ook het verwijderen van een niet-bestaand object!
        int sizeBefore = raceBikes.getSize();
        assertEquals(5, sizeBefore, "Initial size should be 5");

        assertTrue(raceBikes.remove(bike1), "Removing existing bike should return true");
        assertEquals(sizeBefore - 1, raceBikes.getSize(), "Size should decrease by 1");

        RaceBike nonExistentBike = new RaceBike(Brand.CANYON, "Aeroad", Color.WHITE);
        assertFalse(raceBikes.remove(nonExistentBike),
                "Removing non-existent bike should return false");
        assertEquals(sizeBefore - 1, raceBikes.getSize(),
                "Size should remain unchanged after removing non-existent bike");
    }

    @Test
    void testSortByBrand() {
        // 2.2.e Voorzie een methode om het sorteren te testen. Gebruik assertAll om een 3-tal posities te
        //controleren.
        List<RaceBike> sorted = raceBikes.sortedBy(RaceBike::getBrand);

        assertAll("Sorted by brand",
                () -> assertEquals(Brand.BIANCHI, sorted.get(0).getBrand(),
                        "First bike should be BIANCHI"),
                () -> assertEquals(Brand.COLNAGO, sorted.get(1).getBrand(),
                        "Second bike should be COLNAGO"),
                () -> assertEquals(Brand.TREK, sorted.get(4).getBrand(),
                        "Last bike should be TREK")
        );
    }

    @Test
    void testSortByPrice() {
        // 2.2.f TVoorzie nog een methode om het sorteren op een ander criterium te testen. Maak een array met
        //daarin de 5 basisobjecten in de juiste volgorde. Gebruik dan assertArrayEquals om te
        //vergelijken met de gesorteerde list (myList.toArray())
        RaceBike[] expectedOrder = {bike2, bike3, bike1, bike4, bike5};

        List<RaceBike> sorted = raceBikes.sortedBy(RaceBike::getPrice);

        assertArrayEquals(expectedOrder, sorted.toArray(),
                "Bikes should be sorted by price in ascending order");
    }
}