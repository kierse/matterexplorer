package com.pissiphany.matterexplorer.model.model;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pissiphany.matterexplorer.di.annotation.AutoGson;
import com.pissiphany.matterexplorer.model.provider.contract.MatterContract;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

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

    @Nullable public abstract String getDescription();
    @Nullable public abstract String getDisplayNumber();
    @Nullable public abstract String getStatus();
    @Nullable public abstract String getBillingMethod();
    @Nullable public abstract Boolean getBillable();
    @Nullable public abstract LocalDate getPendingDate();
    @Nullable public abstract LocalDate getOpenDate();
    @Nullable public abstract LocalDate getCloseDate();

    @AutoParcel.Builder
    public abstract static class Builder extends Persistable.Builder<Builder> {
        public abstract Builder setId(Long id);
        public abstract Builder setCreatedAt(DateTime createdAt);
        public abstract Builder setUpdatedAt(DateTime updatedAt);

        public abstract Builder setDescription(String description);
        public abstract Builder setDisplayNumber(String displayNumber);
        public abstract Builder setStatus(String status);
        public abstract Builder setBillingMethod(String billingMethod);
        public abstract Builder setBillable(Boolean billable);
        public abstract Builder setPendingDate(LocalDate pendingDate);
        public abstract Builder setOpenDate(LocalDate openDate);
        public abstract Builder setCloseDate(LocalDate closeDate);

        public abstract Matter build();

        @Override
        public Builder fromCursor(@NonNull Cursor cursor, @NonNull String prefix) {
            int index;

            // ID
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.ID)) > -1) {
                setId(cursor.getLong(index));
            }

            // CREATED_AT
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.CREATED_AT)) > -1) {
                setCreatedAt(new DateTime(cursor.getString(index)));
            }

            // UPDATED_AT
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.UPDATED_AT)) > -1) {
                setUpdatedAt(new DateTime(cursor.getString(index)));
            }

            // DESCRIPTION
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.DESCRIPTION)) > -1) {
                setDescription(cursor.getString(index));
            }

            // DISPLAY_NUMBER
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.DISPLAY_NUMBER)) > -1) {
                setDisplayNumber(cursor.getString(index));
            }

            // STATUS
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.STATUS)) > -1) {
                setStatus(cursor.getString(index));
            }

            // BILLING_METHOD
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.BILLING_METHOD)) > -1) {
                setBillingMethod(cursor.getString(index));
            }

            // BILLABLE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.BILLABLE)) > -1) {
                setBillable(cursor.getInt(index) > -1);
            }

            // PENDING_DATE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.PENDING_DATE)) > -1) {
                setPendingDate(new LocalDate(cursor.getString(index)));
            }
            // OPEN_DATE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.OPEN_DATE)) > -1) {
                setOpenDate(new LocalDate(cursor.getString(index)));
            }

            // CLOSE_DATE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.CLOSE_DATE)) > -1) {
                setCloseDate(new LocalDate(cursor.getString(index)));
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

        values.put(MatterContract.Columns.DESCRIPTION, getDescription());
        values.put(MatterContract.Columns.DISPLAY_NUMBER, getDisplayNumber());
        values.put(MatterContract.Columns.STATUS, getStatus());
        values.put(MatterContract.Columns.BILLING_METHOD, getBillingMethod());
        values.put(MatterContract.Columns.BILLABLE, getBillable());

        if (getPendingDate() != null) {
            values.put(MatterContract.Columns.PENDING_DATE, getPendingDate().toString());
        }
        if (getOpenDate() != null) {
            values.put(MatterContract.Columns.OPEN_DATE, getOpenDate().toString());
        }
        if (getCloseDate() != null) {
            values.put(MatterContract.Columns.CLOSE_DATE, getCloseDate().toString());
        }

        return values;
    }
}
