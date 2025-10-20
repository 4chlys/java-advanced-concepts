package be.kdg.persist;

import be.kdg.data.Data;
import be.kdg.model.RaceBike;
import be.kdg.model.RaceBikes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class RaceBikesSerializerTest {
    private RaceBikesSerializer rbs;
    private RaceBikes rb;

    @BeforeEach
    void setUp() {
        rbs = new RaceBikesSerializer("racebikestest");
        rb = new RaceBikes();
        Data.getData().forEach(rb::add);
    }

    @Test
    void testSerialize() {
        assertDoesNotThrow(() -> rbs.serialize(rb));
    }



    @Test
    void testDeserialize() throws IOException, ClassNotFoundException {
        rbs.serialize(rb);
        RaceBikes deserialized = rbs.deserialize();
        assertEquals(rb, deserialized);
    }
}
