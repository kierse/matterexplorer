package com.pissiphany.matterexplorer.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pissiphany.matterexplorer.provider.contract.BaseContract;

import org.joda.time.DateTime;

/**
 * Created by kierse on 15-09-22.
 */
public abstract class Persistable {
    static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Nullable public abstract Long getId();
    @Nullable public abstract DateTime getCreatedAt();
    @Nullable public abstract DateTime getUpdatedAt();

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(BaseContract.Columns.ID, getId());

        if (getCreatedAt() != null) {
            values.put(BaseContract.Columns.CREATED_AT, getCreatedAt().toString());
        }
        if (getUpdatedAt() != null) {
            values.put(BaseContract.Columns.UPDATED_AT, getUpdatedAt().toString());
        }

        return values;
    }

    static abstract class Builder<T> {
        int getColumnIndex(Cursor cursor, String prefix, @NonNull String column) {
            if (!prefix.isEmpty()) prefix += "_";
            return cursor.getColumnIndex(prefix + column);
        }

        public T fromCursor(@NonNull Cursor cursor) {
            return fromCursor(cursor, "");
        }

        public abstract T fromCursor(@NonNull Cursor cursor, @NonNull String prefix);
    }
}
