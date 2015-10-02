package com.pissiphany.matterexplorer.network.api.themis.response;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by kierse on 15-09-21.
 */
public interface BaseResponseV2 {
    int getRecords();
    int getLimit();
    int getNextOffset();
    int getTotalRecords();

    @NonNull
    String getOrderDir();

    @NonNull
    Date getPublishedAt();

}
