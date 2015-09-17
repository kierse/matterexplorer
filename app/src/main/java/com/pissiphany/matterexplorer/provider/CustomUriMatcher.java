package com.pissiphany.matterexplorer.provider;

import android.content.UriMatcher;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kierse on 15-08-30.
 */
public class CustomUriMatcher {
    private String mAuthority;
    private UriMatcher mMatcher;
    private Map<Integer, String> mTables;
    private Map<Integer, String> mContentTypes;

    public CustomUriMatcher(@NonNull String authority) {
        mAuthority = authority;
        mMatcher = new android.content.UriMatcher(android.content.UriMatcher.NO_MATCH);
        mTables = new HashMap<>();
        mContentTypes = new HashMap<>();
    }

    public void addUri(String table, String contentType, @NonNull String path, int code) {
        mMatcher.addURI(mAuthority, table + path, code);

        if (!isBlank(table)) mTables.put(code, table);
        if (!isBlank(contentType)) mContentTypes.put(code, contentType);
    }

    public int getMatchCode(Uri uri) {
        return mMatcher.match(uri);
    }

    public String getMatchTable(Uri uri) {
        int code = mMatcher.match(uri);
        if (code != android.content.UriMatcher.NO_MATCH) {
            return mTables.get(code);
        }

        return null;
    }

    public String getMatchContentType(Uri uri) {
        int code = mMatcher.match(uri);
        if (code != mMatcher.NO_MATCH) {
            return mContentTypes.get(code);
        }

        return null;
    }

    private boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }
}
