package be.kdg.parsing;

import be.kdg.model.RaceBikes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RaceBikesGsonParser {
    public static void writeJson(RaceBikes bikes, String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateGsonAdapter())
                .registerTypeAdapter(BigDecimal.class, new BigDecimalGsonAdapter())
                .setPrettyPrinting()
                .create();

        try (FileWriter fw = new FileWriter(fileName)) {
            gson.toJson(bikes, fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static RaceBikes readJson(String fileName) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateGsonAdapter())
                .registerTypeAdapter(BigDecimal.class, new BigDecimalGsonAdapter())
                .create();

        try (FileReader fr = new FileReader(fileName)) {
            return gson.fromJson(fr, RaceBikes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}