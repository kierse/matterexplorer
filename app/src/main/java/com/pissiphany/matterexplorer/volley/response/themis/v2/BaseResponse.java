package com.pissiphany.matterexplorer.volley.response.themis.v2;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by kierse on 15-09-21.
 */
public interface BaseResponse {
    int getRecords();
    int getLimit();
    int getNextOffset();
    int getTotalRecords();

    @NonNull
    String getOrderDir();

    @NonNull
    Date getPublishedAt();

}
