package com.pissiphany.matterexplorer.model;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pissiphany.matterexplorer.annotation.AutoGson;
import com.pissiphany.matterexplorer.provider.contract.MatterContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import auto.parcel.AutoParcel;

/**
 * Created by kierse on 15-09-08.
 */
@AutoParcel
@AutoGson(autoParcelClass = AutoParcel_Matter.class)
public abstract class Matter extends PersistableParent implements Parcelable {
    private static final List<Class<? extends Persistable>> CLASSES = new ArrayList<>();
    static {
        CLASSES.add(Matter.class);
    }

    @Nullable public abstract String getName();
    @Nullable public abstract String getDescription();
    @Nullable public abstract String getDisplayNumber();
    @Nullable public abstract String getStatus();
    @Nullable public abstract String getBillingMethod();
    @Nullable public abstract Boolean getBillable();
    @Nullable public abstract Date getPendingDate();
    @Nullable public abstract Date getOpenDate();
    @Nullable public abstract Date getCloseDate();

    @AutoParcel.Builder
    public abstract static class Builder extends Persistable.Builder<Builder> {
        public abstract Builder setId(Long id);
        public abstract Builder setCreatedAt(Date createdAt);
        public abstract Builder setUpdatedAt(Date updatedAt);

        public abstract Builder setName(String name);
        public abstract Builder setDescription(String description);
        public abstract Builder setDisplayNumber(String displayNumber);
        public abstract Builder setStatus(String status);
        public abstract Builder setBillingMethod(String billingMethod);
        public abstract Builder setBillable(Boolean billable);
        public abstract Builder setPendingDate(Date pendingDate);
        public abstract Builder setOpenDate(Date openDate);
        public abstract Builder setCloseDate(Date closeDate);

        public abstract Matter build();

        @Override
        public Builder fromCursor(@NonNull Cursor cursor, @NonNull String prefix) {
            int index;
            SimpleDateFormat formater = new SimpleDateFormat(Persistable.ISO_8601, Locale.US);

            // ID
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.ID)) > 0) {
                setId(cursor.getLong(index));
            }

            // CREATED_AT
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.CREATED_AT)) > 0) {
                try {
                    setCreatedAt(formater.parse(cursor.getString(index)));
                } catch (ParseException e) { }
            }

            // UPDATED_AT
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.UPDATED_AT)) > 0) {
                try {
                    setCreatedAt(formater.parse(cursor.getString(index)));
                } catch (ParseException e) { }
            }

            // NAME
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.NAME)) > 0) {
                setDescription(cursor.getString(index));
            }

            // DESCRIPTION
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.DESCRIPTION)) > 0) {
                setDescription(cursor.getString(index));
            }

            // DISPLAY_NUMBER
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.DISPLAY_NUMBER)) > 0) {
                setDescription(cursor.getString(index));
            }

            // STATUS
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.STATUS)) > 0) {
                setStatus(cursor.getString(index));
            }

            // BILLING_METHOD
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.BILLING_METHOD)) > 0) {
                setStatus(cursor.getString(index));
            }

            // BILLABLE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.BILLABLE)) > 0) {
                setBillable(cursor.getInt(index) > 0);
            }

            // PENDING_DATE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.PENDING_DATE)) > 0) {
                try {
                    setCreatedAt(formater.parse(cursor.getString(index)));
                } catch (ParseException e) { }
            }
            // OPEN_DATE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.OPEN_DATE)) > 0) {
                try {
                    setOpenDate(formater.parse(cursor.getString(index)));
                } catch (ParseException e) { }
            }

            // CLOSE_DATE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.CLOSE_DATE)) > 0) {
                try {
                    setOpenDate(formater.parse(cursor.getString(index)));
                } catch (ParseException e) { }
            }

            return this;
        }
    }

    // this method can be used to convert an object into a builder
    public abstract Builder toBuilder();

    // set any/all defaults here
    public static Builder builder() {
        return new AutoParcel_Matter.Builder();
    }

    // This does two things:
    //   1. it prevents undocumented public constructor
    //   2. prevents outside packages from subclassing
    Matter() { }

    @Override
    public List<Class<? extends Persistable>> getPersistableClasses() {
        return CLASSES;
    }

    @Override
    public List<Persistable> getPersistablesOfType(Class<? extends Persistable> klass) {
        List<Persistable> persistables = new ArrayList<>();
        if (klass == Matter.class) {
            persistables.add(this);
        }

        return persistables;
    }

    @Override
    public Uri getContentUri() {
        return MatterContract.CONTENT_URI;
    }

    @Override
    public List<ContentProviderOperation> getDeleteOperations() {
        return super.getDeleteOperations();
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = super.getContentValues();

        values.put(MatterContract.Columns.NAME, getName());
        values.put(MatterContract.Columns.DESCRIPTION, getDescription());
        values.put(MatterContract.Columns.DISPLAY_NUMBER, getDisplayNumber());
        values.put(MatterContract.Columns.STATUS, getStatus());
        values.put(MatterContract.Columns.BILLING_METHOD, getBillingMethod());
        values.put(MatterContract.Columns.BILLABLE, getBillable());

        SimpleDateFormat formatter = new SimpleDateFormat(Persistable.ISO_8601);
        if (getPendingDate() != null) {
            values.put(MatterContract.Columns.PENDING_DATE, formatter.format(getPendingDate()));
        }
        if (getOpenDate() != null) {
            values.put(MatterContract.Columns.OPEN_DATE, formatter.format(getOpenDate()));
        }
        if (getCloseDate() != null) {
            values.put(MatterContract.Columns.CLOSE_DATE, formatter.format(getCloseDate()));
        }

        return values;
    }
}
