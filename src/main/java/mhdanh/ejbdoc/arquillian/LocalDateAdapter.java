package mhdanh.ejbdoc.arquillian;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * using for serialize and deserialize between LocalDate and json element with format "yyyy-MM-dd" for luzfin_finance Rest api
 *
 */
public interface LocalDateAdapter extends JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
	public String dateTimePattern();

	public default GsonBuilder registerTo(GsonBuilder builder) {
		return builder.registerTypeAdapter(new TypeToken<LocalDate>() {}.getType(), this);
	}

	@Override
	public default JsonElement serialize(LocalDate localDate, Type type, com.google.gson.JsonSerializationContext context) {
		return new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern(dateTimePattern())));
	}

	@Override
	public default LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
		return LocalDate.parse(jsonElement.getAsString(), DateTimeFormatter.ofPattern(dateTimePattern()));
	}
}


