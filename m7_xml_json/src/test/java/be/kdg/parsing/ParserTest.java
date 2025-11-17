package be.kdg.parsing;

import be.kdg.data.Data;
import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    private RaceBikes raceBikes;
    private static final Path DATA_DIR = Paths.get("datafiles");

    @BeforeEach
    void setUp() {
        List<RaceBike> raceBikeList = Data.getData();
        raceBikes = new RaceBikes();
        for (RaceBike rb : raceBikeList) {
            raceBikes.add(rb);
        }
    }

    @Test
    void testStaxDom() throws Exception {
        Path xmlFile = DATA_DIR.resolve("staxRaceBikes.xml");

        RaceBikesStaxParser staxParser = new RaceBikesStaxParser(raceBikes, xmlFile.toString());
        assertDoesNotThrow(staxParser::staxWriteXML, "StAX writer should not throw");

        String xmlContent = java.nio.file.Files.readString(xmlFile);
        assertTrue(xmlContent.contains("brand=") || xmlContent.contains("model="),
                "Expected at least one property to be written as an XML attribute");

        RaceBikes readRaceBikes = RaceBikesDomParser.domReadXML(xmlFile.toString());

        assertEquals(raceBikes.getSize(), readRaceBikes.getSize(), "Size must match after DOM read");

        List<RaceBike> original = raceBikes.getRaceBikeList();
        List<RaceBike> read = readRaceBikes.getRaceBikeList();

        IntStream.range(0, original.size()).forEach(i -> {
            RaceBike o = original.get(i);
            RaceBike r = read.get(i);
            assertAll("bike " + i,
                    () -> assertEquals(o.getBrand(), r.getBrand(), "brand"),
                    () -> assertEquals(o.getModel(), r.getModel(), "model")
            );
        });
    }

    @Test
    void testJaxb() {
        Path xmlFile = DATA_DIR.resolve("jaxbRaceBikes.xml");

        assertDoesNotThrow(() -> RaceBikesJaxbParser.JaxbWriteXml(xmlFile.toString(), raceBikes),
                "JAXB write should not throw");

        RaceBikes readRaceBikes = RaceBikesJaxbParser.JaxbReadXml(xmlFile.toString(), RaceBikes.class);

        assertEquals(raceBikes.getSize(), readRaceBikes.getSize());

        List<RaceBike> original = raceBikes.getRaceBikeList();
        List<RaceBike> read = readRaceBikes.getRaceBikeList();

        IntStream.range(0, original.size()).forEach(i -> {
            RaceBike o = original.get(i);
            RaceBike r = read.get(i);
            assertAll("jaxb bike " + i,
                    () -> assertEquals(o.getBrand(), r.getBrand()),
                    () -> assertEquals(o.getModel(), r.getModel())
            );
        });
    }

    @Test
    void testGson() {
        Path jsonFile = DATA_DIR.resolve("gsonRaceBikes.json");

        assertDoesNotThrow(() -> RaceBikesGsonParser.writeJson(raceBikes, jsonFile.toString()));

        RaceBikes readRaceBikes = RaceBikesGsonParser.readJson(jsonFile.toString());

        assertEquals(raceBikes.getSize(), readRaceBikes.getSize());

        List<RaceBike> original = raceBikes.getRaceBikeList();
        List<RaceBike> read = readRaceBikes.getRaceBikeList();

        IntStream.range(0, original.size()).forEach(i -> {
            RaceBike o = original.get(i);
            RaceBike r = read.get(i);
            assertAll("gson bike " + i,
                    () -> assertEquals(o.getBrand(), r.getBrand()),
                    () -> assertEquals(o.getModel(), r.getModel())
            );
        });
    }
}
