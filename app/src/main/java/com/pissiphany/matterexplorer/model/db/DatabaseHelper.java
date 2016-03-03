package com.pissiphany.matterexplorer.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pissiphany.matterexplorer.model.provider.contract.MatterContract;

/**
 * Created by kierse on 15-08-23.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "matter_explorer.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "" +
            "CREATE TABLE " + MatterContract.TABLE + " "
                + "("
                + MatterContract.Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MatterContract.Columns.ID + " INTEGER UNIQUE, "
                + MatterContract.Columns.UPDATED_AT + " TEXT, "
                + MatterContract.Columns.CREATED_AT + " TEXT, "
                + MatterContract.Columns.DESCRIPTION + " TEXT, "
                + MatterContract.Columns.DISPLAY_NUMBER + " TEXT, "
                + MatterContract.Columns.STATUS + " TEXT, "
                + MatterContract.Columns.PENDING_DATE + " TEXT, "
                + MatterContract.Columns.OPEN_DATE + " TEXT, "
                + MatterContract.Columns.CLOSE_DATE + " TEXT, "
                + MatterContract.Columns.BILLING_METHOD + " TEXT, "
                + MatterContract.Columns.BILLABLE + " INTEGER DEFAULT 1"
            + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // DO NOTHING! No support for upgrading the database at this point
    }
}
