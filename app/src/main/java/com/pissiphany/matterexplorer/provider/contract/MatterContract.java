package com.pissiphany.matterexplorer.provider.contract;

import android.content.ContentUris;
import android.net.Uri;

/**
 * Created by kierse on 15-08-23.
 */
public class MatterContract extends BaseContract {
    public static final String TABLE = "matters";

    public static final String CONTENT_TYPE = BASE_CONTENT_TYPE + TABLE;
    public static final String CONTENT_ITEM_TYPE = BASE_CONTENT_TYPE + TABLE;

    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(TABLE)
            .build();

    public static class Columns extends BaseContract.Columns {
        public static final String DESCRIPTION = "description";
        public static final String DISPLAY_NUMBER = "display_number";
        public static final String STATUS = "status";
        public static final String PENDING_DATE = "pending_date";
        public static final String OPEN_DATE = "open_date";
        public static final String CLOSE_DATE = "close_date";
        public static final String BILLING_METHOD = "billing_method";
        public static final String BILLABLE = "billable";
    }

    public static Uri buildSelectionUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
