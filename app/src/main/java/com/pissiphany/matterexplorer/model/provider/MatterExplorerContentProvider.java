package com.pissiphany.matterexplorer.model.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.pissiphany.matterexplorer.model.db.DatabaseHelper;
import com.pissiphany.matterexplorer.model.provider.contract.BaseContract;
import com.pissiphany.matterexplorer.model.provider.contract.MatterContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kierse on 15-08-23.
 */
public class MatterExplorerContentProvider extends ContentProvider {

    private static final int MATTERS = 100;
    private static final int MATTER_ID = 101;

    // TODO port
    private  static final CustomUriMatcher sUriMatcher = new CustomUriMatcher(BaseContract.AUTHORITY);
    static {
        sUriMatcher.addUri(MatterContract.TABLE, MatterContract.CONTENT_TYPE, "", MATTERS);
        sUriMatcher.addUri(MatterContract.TABLE, MatterContract.CONTENT_ITEM_TYPE, "/#", MATTER_ID);
    }

    private DatabaseHelper mDatabaseHelper;

    // TODO port
    // this is MUCH simpler than our current SelectionBuilder. This combined with SQLiteQueryBuilder
    // should allow us to get rid of our current implementation.
    private static class SelectionBuilder {
        private List<String> mSelections;
        private List<String> mSelectionArgs;

        public SelectionBuilder() {
            mSelections = new ArrayList<>();
            mSelectionArgs = new ArrayList<>();
        }

        public void add(String selection, String... args) {
            if (selection != null && !selection.isEmpty()) {
                mSelections.add(selection);
                if (args.length > 0) mSelectionArgs.addAll(Arrays.asList(args));
            }
        }

        public String getSelection() {
            return mSelections.size() > 0
                ? TextUtils.join(" AND ", mSelections)
                : null;
        }

        public String[] getSelectionArgs() {
            String[] args = null;
            if (mSelectionArgs.size() > 0) {
                args = mSelectionArgs.toArray(new String[mSelectionArgs.size()]);
            }

            return args;
        }
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int count = 0;
        String table = getTable(uri);

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (int i = 0; i < values.length; i++) {
                long rowId = upsert(table, values[i]);
                if (rowId > -1) count++;
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        if (count > 0) notifyChange(uri);

        return count;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String table = getTable(uri);
        if (table != null) {
            long rowId = upsert(table, values);

            Uri rowPath = null;
            if (rowId > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
                rowPath = ContentUris.withAppendedId(uri, rowId);
            }

            return rowPath;
        }

        throw new UnsupportedOperationException("unknown uri: " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        builder.add(selection, selectionArgs);

        Long id = extractRecordId(uri);
        if (id != null) {
            builder.add("id = ?", id.toString());
        }

        int result = mDatabaseHelper.getWritableDatabase()
                .update(getTable(uri), values, builder.getSelection(), builder.getSelectionArgs());
        if (result > 0) getContext().getContentResolver().notifyChange(uri, null);

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SelectionBuilder builder = new SelectionBuilder();
        builder.add(selection, selectionArgs);

        Long id = extractRecordId(uri);
        if (id != null) {
            builder.add("id = ?", id.toString());
        }

        int result = mDatabaseHelper.getWritableDatabase()
                .delete(getTable(uri), builder.getSelection(), builder.getSelectionArgs());
        if (result > 0) getContext().getContentResolver().notifyChange(uri, null);

        return result;
    }

    private long upsert(String table, ContentValues values) {
        return mDatabaseHelper
                .getWritableDatabase()
                .insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder builder = getQueryBuilder(uri);

        Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        if (cursor != null) cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        String type = sUriMatcher.getMatchContentType(uri);
        if (type != null) return type;

        throw new IllegalArgumentException("unknown type: " + uri);
    }

    private String getTable(Uri uri) {
        String table = sUriMatcher.getMatchTable(uri);
        if (table != null) return table;

        throw new UnsupportedOperationException("unknown table: " + uri);
    }

    // TODO port
    // this is better than checking if the matching number ends in a 1, which is bound to fail

    private Long extractRecordId(Uri uri) {
        Long id = null;
        try {
            id = Long.valueOf(uri.getLastPathSegment());
        } catch (NumberFormatException e) {
            // do nothing, Uri obviously doesn't end in a long
        }

        return id;
    }

    private SQLiteQueryBuilder getQueryBuilder(Uri uri) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(getTable(uri));

        // TODO comment on this
        builder.setStrict(true);

//        switch (sUriMatcher.getMatchCode(uri)) {
//            default:
//
//        }

        Long id = extractRecordId(uri);
        if (id != null) {
            builder.appendWhere("id = " + id.toString());
        }

        return builder;
    }

    private void notifyChange(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
}
