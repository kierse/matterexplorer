package com.pissiphany.matterexplorer.network.api.themis.response;

import android.support.annotation.Nullable;

import java.util.Date;

/**
 * Created by kierse on 15-09-21.
 */
public interface BaseResponseV2 {
    int getRecords();
    int getLimit();
    int getNextOffset();
    int getTotalRecords();

    @Nullable
    String getOrderDir();

    @Nullable
    Date getPublishedAt();
}
