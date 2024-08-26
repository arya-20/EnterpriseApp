package example.restaurant.application.events;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import example.restaurant.domain.MenuItem;

import java.io.IOException;

public class MenuItemCustomSerializer extends JsonSerializer<MenuItem> {
    @Override
    public void serialize(MenuItem menuItem, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", String.valueOf(menuItem.id())); //convert long to String
        jsonGenerator.writeStringField("name", menuItem.name());
        jsonGenerator.writeNumberField("price", menuItem.price().asBigDecimal());
        jsonGenerator.writeEndObject();
    }
}
