package com.pissiphany.matterexplorer.model;

import android.content.ContentValues;
import android.support.annotation.Nullable;

import com.pissiphany.matterexplorer.provider.contract.BaseContract;

import java.util.Date;

/**
 * Created by kierse on 15-09-22.
 */
public abstract class Persistable {
    @Nullable public abstract Long getId();
    @Nullable public abstract Date getCreatedAt();
    @Nullable public abstract Date getUpdatedAt();

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(BaseContract.Columns.ID, getId());

        return values;
    }
}
