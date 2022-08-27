package uz.sngroup.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uz.sngroup.model.event.Event;
import java.io.IOException;

public class EventSerializer extends StdSerializer<Event> {
    public EventSerializer() {
        this(null);
    }
    public EventSerializer(Class<Event> t) {
        super(t);
    }

    @Override
    public void serialize(Event event, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", event.getId());
        jgen.writeNumberField("serial", event.getSerial());
        jgen.writeStringField("ean", event.getEan());
        jgen.writeNumberField("width", event.getWidth());
        jgen.writeNumberField("height", event.getHeight());
        jgen.writeNumberField("m2", event.getM2());
        jgen.writeNumberField("count", event.getCount());
        jgen.writeNumberField("pieces", event.getPieces());
        jgen.writeNumberField("weight", event.getWeight());
        jgen.writeStringField("description", event.getDescription());
        jgen.writeStringField("notes", event.getNotes());
        jgen.writeStringField("eventType", event.getEventType().toString());
        jgen.writeStringField("productName", event.getProduct().getName());
        jgen.writeStringField("color", event.getProduct().getColor().getName());
        jgen.writeStringField("gramm", event.getProduct().getGramm().getWeight());
        jgen.writeStringField("user", event.getUser().getLogin());
        jgen.writeStringField("date", event.getDate().toString());
        jgen.writeStringField("modified", event.getModified().toString());
        jgen.writeEndObject();
    }
}

