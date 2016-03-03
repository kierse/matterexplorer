package com.pissiphany.matterexplorer.model.network.api;

import android.os.Parcelable;

import com.pissiphany.matterexplorer.model.model.PersistableParent;

import java.util.List;

/**
 * Created by kierse on 15-09-21.
 */
public abstract class ParcelableApiResponse implements Parcelable {
     public abstract List<PersistableParent> getPersistableParents();
}
