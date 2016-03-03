package com.pissiphany.matterexplorer.model.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.DateTime;

import java.lang.reflect.Type;

/**
 * Created by kierse on 15-10-05.
 */
public class DateTimeTypeAdapter implements JsonDeserializer<DateTime> {
    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            return DateTime.parse(json.getAsString());
        } catch (IllegalArgumentException e) {
            throw new JsonParseException("Unparseable DateTime: " + json.getAsString());
        }
    }
}
