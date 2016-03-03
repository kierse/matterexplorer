package com.pissiphany.matterexplorer.model.db;

import android.content.ContentProviderOperation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kierse on 15-09-23.
 */
public class DeleteDirector implements Iterator<ArrayList<ContentProviderOperation>> {
    private static final int ITERATION_LIMIT = 200;

    private List<ContentProviderOperation> mDeletes;
    private int mCounter = 0;

    public DeleteDirector(List<ContentProviderOperation> deletes) {
        this.mDeletes = deletes;
    }

    @Override
    public boolean hasNext() {
        return mDeletes.size() > mCounter + 1;
    }

    @Override
    public ArrayList<ContentProviderOperation> next() {
        int end = (mCounter + ITERATION_LIMIT > mDeletes.size())
                ? mDeletes.size()
                : mCounter + ITERATION_LIMIT;
        // TODO is there a better way to do this??!!
        ArrayList<ContentProviderOperation> next = new ArrayList<>(mDeletes.subList(mCounter, end));
        mCounter = end;

        return next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove not supported");
    }
}
