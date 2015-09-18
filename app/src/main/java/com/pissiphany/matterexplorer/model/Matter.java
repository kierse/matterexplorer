package com.pissiphany.matterexplorer.model;

import android.database.Cursor;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pissiphany.matterexplorer.annotation.AutoGson;
import com.pissiphany.matterexplorer.provider.contract.MatterContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import auto.parcel.AutoParcel;

/**
 * Created by kierse on 15-09-08.
 */
@AutoParcel
@AutoGson(autoParcelClass = AutoParcel_Matter.class)
public abstract class Matter implements Parcelable {
    private static final String ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    @Nullable public abstract Long getId();
    @Nullable public abstract Date getCreatedAt();
    @Nullable public abstract Date getUpdatedAt();

    @Nullable public abstract String getName();
    @Nullable public abstract String getDescription();
    @Nullable public abstract String getDisplayNumber();
    @Nullable public abstract String getStatus();
    @Nullable public abstract String getClientReference();
    @Nullable public abstract String getLocation();
    @Nullable public abstract String getMaildropAddress();
    @Nullable public abstract String getBillingMethod();
    @Nullable public abstract Boolean getBillable();
    @Nullable public abstract Date getPendingDate();
    @Nullable public abstract Date getOpenDate();
    @Nullable public abstract Date getCloseDate();

    // this method can be used to convert an object into a builder
    public abstract Builder toBuilder();

    // This does two things:
    //   1. it prevents undocumented public constructor
    //   2. prevents outside packages from subclassing
    Matter() { }

    // set any/all defaults here
    public static Builder builder() {
        return new AutoParcel_Matter.Builder();
    }

    @AutoParcel.Builder
    public abstract static class Builder {
        public abstract Builder setId(Long id);
        public abstract Builder setCreatedAt(Date createdAt);
        public abstract Builder setUpdatedAt(Date updatedAt);

        public abstract Builder setName(String name);
        public abstract Builder setDescription(String description);
        public abstract Builder setDisplayNumber(String displayNumber);
        public abstract Builder setStatus(String status);
        public abstract Builder setClientReference(String clientReference);
        public abstract Builder setLocation(String location);
        public abstract Builder setMaildropAddress(String maildropAddress);
        public abstract Builder setBillingMethod(String billingMethod);
        public abstract Builder setBillable(Boolean billable);
        public abstract Builder setPendingDate(Date pendingDate);
        public abstract Builder setOpenDate(Date openDate);
        public abstract Builder setCloseDate(Date closeDate);

        public abstract Matter build();

        public Builder fromCursor(@NonNull Cursor cursor) {
            return fromCursor(cursor, "");
        }

        public Builder fromCursor(@NonNull Cursor cursor, @NonNull String prefix) {
            int index;

            // ID
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.ID)) > 0) {
                setId(cursor.getLong(index));
            }

            // DESCRIPTION
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.DESCRIPTION)) > 0) {
                setDescription(cursor.getString(index));
            }

            // STATUS
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.STATUS)) > 0) {
                setStatus(cursor.getString(index));
            }

            // OPEN_DATE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.OPEN_DATE)) > 0) {
                try {
                    new SimpleDateFormat();
                    setOpenDate(new SimpleDateFormat(ISO_8601).parse(cursor.getString(index)));
                } catch (ParseException e) { }
            }

            // BILLABLE
            if ((index = getColumnIndex(cursor, prefix, MatterContract.Columns.BILLABLE)) > 0) {
                setBillable(cursor.getInt(index) == 1);
            }

            return this;
        }

        private int getColumnIndex(Cursor cursor, String prefix, @NonNull String column) {
            if (!prefix.isEmpty()) prefix += "_";
            return cursor.getColumnIndex(prefix + column);
        }
    }
}
