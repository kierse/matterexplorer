package com.pissiphany.matterexplorer.network.api.themis.response;

import android.support.annotation.Nullable;

import com.pissiphany.matterexplorer.annotation.AutoGson;
import com.pissiphany.matterexplorer.model.Matter;
import com.pissiphany.matterexplorer.model.PersistableParent;
import com.pissiphany.matterexplorer.network.api.ParcelableApiResponse;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;

/**
 * Created by kierse on 15-09-21.
 */
@AutoParcel
@AutoGson(autoParcelClass = AutoParcel_MatterResponseV2.class)
public abstract class MatterResponseV2 extends ParcelableApiResponse implements BaseResponseV2 {
    @Nullable
    public abstract List<Matter> getMatters(); // TODO use ImmutableList here

    @Nullable
    public abstract Matter getMatter();

    MatterResponseV2() { }

    @Override
    public List<PersistableParent> getPersistableParents() {
        List<PersistableParent> list = null;
        if (getMatters() != null && getMatters().size() > 0) {
            list = new ArrayList<>(getMatters().size());
            list.addAll(getMatters());
        } else if (getMatter() != null) {
            list = new ArrayList<>(1);
            list.add(getMatter());
        }

        return list;
    }
}
