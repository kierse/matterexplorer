package com.pissiphany.matterexplorer.model.model;

import android.content.ContentProviderOperation;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kierse on 15-09-23.
 */
public abstract class PersistableParent extends Persistable {
    public abstract Uri getContentUri();
    public abstract List<Class<? extends Persistable>> getPersistableClasses();
    public abstract List<Persistable> getPersistablesOfType(Class<? extends Persistable> klass);

    public List<ContentProviderOperation> getDeleteOperations() {
        return new ArrayList<>(0);
    }
}
