package be.kdg.parsing;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class BigDecimalGsonAdapter implements JsonSerializer<BigDecimal>, JsonDeserializer<BigDecimal> {

    @Override
    public JsonElement serialize(BigDecimal src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }

    @Override
    public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return json.getAsBigDecimal();
    }
}
