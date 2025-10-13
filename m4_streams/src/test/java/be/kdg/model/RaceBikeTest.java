package be.kdg.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RaceBikeTest {
    private RaceBike raceBike1;
    private RaceBike raceBike2;

    @BeforeEach
    void setUp() {
        //2.1.b. Kies JUnit5 als Testing Library.Verander de naam van de klasse naar DictatorTest.
        //Verifieer de destination package: DictatorTest moet dezelfde package-structuur hebben als de
        //oorspronkelijke klasse Dictator. Laat de andere opties voorlopig open en bevestig met “OK”
        //2.1.c. Ga naar de gecreëerde testklasse en voorzie daar minstens twee testobjecten van je basisklasse als
        //private attributen.
        //2.1.d. Druk ALT+INS in de test klasse en kies SetUp Method. Gebruik de gegenereerde @BeforeEach
        //methode om de private testobjecten (2.1.c) te initialiseren met bepaalde startwaarden.
        raceBike1 = new RaceBike(Brand.CERVELO, "R5", Color.BLACK, 7.2, 56,
                LocalDate.of(2023, 1, 15), BigDecimal.valueOf(12000), Currency.EUR,
                Groupset.SHIMANO_DURA_ACE_Di2, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);

        raceBike2 = new RaceBike(Brand.COLNAGO, "V4Rs", Color.RED, 8.1, 54,
                LocalDate.of(2022, 6, 10), BigDecimal.valueOf(9500), Currency.EUR,
                Groupset.SRAM_RED_ETAP_AXS, FrameMaterial.CARBON, ForkMaterial.CARBON,
                BrakeType.DISC_HYDRAULIC, WheelSize.ROAD_700C);
    }

    @Test
    void testEquals() {
        // 2.1.e. Schrijf een methode testEquals, daar test je de equals methode van je basisklasse uit als volgt:
        //1. Controleer of beide testobjecten verschillen van elkaar. Voeg een foutboodschap toe aan je
        //test.
        assertNotEquals(raceBike1, raceBike2, "The race bikes must be different");

        // 2.1.e.2 Maak nu een lokaal object van de basisklasse aan waarvan de bepalende eigenschappen gelijk
        //zijn aan die van het eerste testobject, maar andere eigenschappen verschillen. Controleer nu
        //of equals true geeft. Vermeld in je foutboodschap welke eigenschappen gelijk zouden moeten
        //zijn en de effectieve waarden in beide objecten.
        RaceBike sameBike = new RaceBike(Brand.CERVELO, "R5", Color.WHITE, 7.5, 58,
                LocalDate.of(2024, 1, 1), BigDecimal.valueOf(15000), Currency.USD,
                Groupset.SHIMANO_ULTEGRA_Di2, FrameMaterial.ALUMINIUM, ForkMaterial.ALUMINIUM,
                BrakeType.DISC_MECHANICAL, WheelSize.ROAD_700C);

        assertEquals(raceBike1, sameBike,
                () -> String.format("Brand %s and Model %s must be equal. Expected: %s/%s, Actual: %s/%s",
                        raceBike1.getBrand(), raceBike1.getModel(),
                        raceBike1.getBrand(), raceBike1.getModel(),
                        sameBike.getBrand(), sameBike.getModel()));
    }

    @Test
    void testIllegalBrand() {
        // 2.1.f Schrijf een methode testIllegalXxx (bijvoorbeeld: testIllegalName), waarin je een
        //ongeldige waarde aan een setter van de basisklasse meegeeft en dus een
        //IllegalArgumentException verwacht. . Maak daarbij gebruik van assertThrows.
        //Controleer of de test faalt bij een ongeldige waarde. Zorg daarna dat de test slaagt.
        assertThrows(IllegalArgumentException.class,
                () -> raceBike1.setBrand(null),
                "Setting brand to null must throw IllegalArgumentException");
    }

    @Test
    void testLegalBrand() {
        // 2.1.g Schrijf een nieuwe methode testLegalXxx (bijvoorbeeld testLegalName), waarin je met
        //assertDoesNotThrow nagaat of de setter bij een correcte waarde GEEN
        //IllegalArgumentException gooit. Voorzie ook hier een gepaste foutboodschap.
        assertDoesNotThrow(() -> raceBike1.setBrand(Brand.TREK),
                "Setting a valid brand must not throw IllegalArgumentException");
    }

    @Test
    void testCompareTo() {
        // 2.1.h Schrijf een methode testCompareTo waarin je de returnwaarde van compareTo uittest op de
        //twee in setup geïnitialiseerde attributen. Maak ook een nieuw lokaal object van de basisklasse dat
        //gelijk zou moeten zijn aan één van de testobjecten en test of compareTo effectief 0 teruggeeft.
        assertTrue(raceBike1.compareTo(raceBike2) < 0,
                "CERVELO should come before COLNAGO alphabetically");

        assertTrue(raceBike2.compareTo(raceBike1) > 0,
                "COLNAGO should come after CERVELO alphabetically");

        RaceBike equalBike = new RaceBike(Brand.CERVELO, "R5");
        assertEquals(0, raceBike1.compareTo(equalBike),
                "Race bikes with same brand and model must return 0");
    }

    @Test
    void testWeight() {
        // 2.1.i Schrijf een testmethode waarin je de waarde van een double-attribuut test. In ons geval wordt dat:
        //testVictims. Gebruik daarin assertEquals met als parameter een delta-waarde.
        assertEquals(7.2, raceBike1.getWeight(), 0.01,
                "Weight must be 7.2 kg with delta 0.01");

        assertEquals(8.1, raceBike2.getWeight(), 0.01,
                "Weight must be 8.1 kg with delta 0.01");
    }
}