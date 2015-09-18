package com.pissiphany.matterexplorer.gson;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.pissiphany.matterexplorer.annotation.AutoGson;

/**
 * Created by kierse on 15-09-08.
 *
 * Based on ideas found at:
 * https://github.com/frankiesardo/auto-parcel/issues/6
 * https://gist.github.com/JakeWharton/0d67d01badcee0ae7bc9
 * https://gist.github.com/Piasy/ebaf701e506b0b3d096d
 */
public class AutoParcelTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        AutoGson annotation = rawType.getAnnotation(AutoGson.class);
        if (annotation == null) {
            return null;
        }

        return (TypeAdapter<T>) gson.getAdapter(annotation.autoParcelClass());
    }
}
