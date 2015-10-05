package com.pissiphany.matterexplorer.provider.contract;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Created by kierse on 15-08-23.
 */
public class BaseContract {
    public static final String AUTHORITY = "com.pissiphany.matterexplorer";

    public static final Uri BASE_CONTENT_URI = new Uri.Builder()
            .scheme(ContentResolver.SCHEME_CONTENT)
            .authority(AUTHORITY)
            .build();

    public static final String BASE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd.pissiphany.";
    public static final String BASE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.pissiphany.";

    public static class Columns {
        public static final String _ID = "_id";
        public static final String ID = "id";
        public static final String CREATED_AT = "created_at";
        public static final String UPDATED_AT = "updated_at";
    }
}
