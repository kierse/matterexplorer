package com.pissiphany.matterexplorer.network.api;

import android.os.Parcelable;

import com.pissiphany.matterexplorer.model.PersistableParent;

import java.util.List;

/**
 * Created by kierse on 15-09-21.
 */
public abstract class ParcelableApiResponse implements Parcelable {
     public abstract List<PersistableParent> getPersistableParents();
}
