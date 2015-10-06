package com.pissiphany.matterexplorer.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

import java.lang.reflect.Type;

/**
 * Created by kierse on 15-10-05.
 */
public class LocalDateTypeAdapter implements JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return LocalDate.parse(json.getAsString(), DateTimeFormat.forPattern("YYYY-mm-dd"));
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Unparseable LocalDate: " + json.getAsString());
        }
    }
}
