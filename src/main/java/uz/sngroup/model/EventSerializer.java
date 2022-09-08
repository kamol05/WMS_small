package uz.sngroup.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uz.sngroup.model.event.Event;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class EventSerializer extends StdSerializer<Event> {

    private final Calendar cal = Calendar.getInstance();
    
    public EventSerializer() {
        this(null);
    }

    public EventSerializer(Class<Event> t) {
        super(t);
    }

    @Override
    public void serialize(Event event, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {

        cal.setTime(event.getDate());

//        jgen.writeNumberField("id", event.getId());
//        jgen.writeStringField("ean", event.getEan());
//        jgen.writeStringField("description", event.getDescription());
//        jgen.writeStringField("notes", event.getNotes());
//        jgen.writeStringField("user", event.getUser().getLogin());
//        jgen.writeNumberField("count", event.getCount());
        jgen.writeStartObject();
        jgen.writeNumberField("serial", event.getSerial());
        jgen.writeNumberField("width", event.getWidth());
        jgen.writeNumberField("height", event.getHeight());
        jgen.writeNumberField("m2", event.getM2());
        jgen.writeNumberField("pieces", event.getPieces());
        jgen.writeNumberField("weight", event.getWeight());
        jgen.writeStringField("eventType", event.getEventType().toString());
        jgen.writeStringField("productName", event.getProduct().getQuality().getName());
        jgen.writeStringField("color", event.getProduct().getColor().getName());
        jgen.writeStringField("gramm", event.getProduct().getGramm().getWeight());
        jgen.writeStringField("year", String.valueOf(cal.get(Calendar.YEAR)));
        jgen.writeStringField("month", cal.get(Calendar.MONTH)+"_"+cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        jgen.writeStringField("day", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        jgen.writeStringField("hour", String.valueOf(cal.get(Calendar.HOUR_OF_DAY)));
        jgen.writeStringField("minute", String.valueOf(cal.get(Calendar.MINUTE)));
        jgen.writeStringField("second", String.valueOf(cal.get(Calendar.SECOND)));
        jgen.writeEndObject();
    }
}

