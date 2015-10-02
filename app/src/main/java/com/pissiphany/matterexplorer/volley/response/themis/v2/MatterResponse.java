package com.pissiphany.matterexplorer.volley.response.themis.v2;

import com.pissiphany.matterexplorer.annotation.AutoGson;
import com.pissiphany.matterexplorer.model.Matter;
import com.pissiphany.matterexplorer.model.PersistableParent;
import com.pissiphany.matterexplorer.volley.response.ParcelableApiResponse;

import java.util.ArrayList;
import java.util.List;

import auto.parcel.AutoParcel;

/**
 * Created by kierse on 15-09-21.
 */
@AutoParcel
@AutoGson(autoParcelClass = AutoParcel_MatterResponse.class)
public abstract class MatterResponse extends ParcelableApiResponse implements BaseResponse {
    public abstract List<Matter> getMatters(); // TODO use ImmutableList here
    public abstract Matter getMatter();

    MatterResponse() { }

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
