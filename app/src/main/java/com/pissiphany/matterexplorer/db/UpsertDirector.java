package com.pissiphany.matterexplorer.db;

import android.content.ContentValues;
import android.net.Uri;

import com.pissiphany.matterexplorer.model.Persistable;
import com.pissiphany.matterexplorer.model.PersistableParent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by kierse on 15-09-23.
 */
public class UpsertDirector implements Iterator<ContentValues[]> {
    private static final int ITERATION_LIMIT = 200;

    private List<PersistableParent> mPersistableParents;
    private List<Class<? extends Persistable>> mClasses;

    private int mClassCount = -1;
    private List<Persistable> mUnprocessedPersistables;

    public UpsertDirector(List<PersistableParent> persistableParents) {
        this.mPersistableParents = persistableParents;
        this.mClasses = persistableParents.get(0).getPersistableClasses();
        this.mUnprocessedPersistables = new ArrayList<>();

        identifyPersistablesForNextClass();
    }

    public Uri getCurrentUri() {
        // TODO return content uri for all nested objects
        return mPersistableParents.get(0).getContentUri();
    }

    @Override
    public boolean hasNext() {
        return (mUnprocessedPersistables.size() > 0 || mClassCount + 1 < mClasses.size());
    }

    @Override
    public ContentValues[] next() {
        ContentValues[] values = null;
        if (hasNext()) {
            if (mUnprocessedPersistables.size() == 0) {
                identifyPersistablesForNextClass();
            }

            List<Persistable> next;
            if (mUnprocessedPersistables.size() > ITERATION_LIMIT) {
                next = mUnprocessedPersistables.subList(0, ITERATION_LIMIT);
                mUnprocessedPersistables = mUnprocessedPersistables
                        .subList(ITERATION_LIMIT, mUnprocessedPersistables.size());
            } else {
                next = new ArrayList<>(mUnprocessedPersistables);
                mUnprocessedPersistables = new ArrayList<>();
            }

            values = new ContentValues[next.size()];
            for (int i = 0; i < next.size(); i++) {
                values[i] = next.get(i).getContentValues();
            }
        }

        return values;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove is unsupported");
    }

    private void identifyPersistablesForNextClass() {
        if (mUnprocessedPersistables.isEmpty()) {
            Class<? extends Persistable> klass = mClasses.get(++mClassCount);
            for (PersistableParent parent : mPersistableParents) {
                mUnprocessedPersistables.addAll(parent.getPersistablesOfType(klass));
            }
        }
    }
}
