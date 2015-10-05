package com.pissiphany.matterexplorer.network.event;

import android.net.Uri;

import com.android.volley.Request;
import com.pissiphany.matterexplorer.network.api.ParcelableApiResponse;
import com.pissiphany.matterexplorer.network.api.SaveableApiResponse;

/**
 * Created by kierse on 15-09-13.
 */
public class GetAndSaveEvent implements NetworkEvent {
    private Class<? extends ParcelableApiResponse> mResponseClass;
    private Uri mUri;

    private Request.Priority mPriority;

    public GetAndSaveEvent(Class<? extends ParcelableApiResponse> clazz, Uri uri, Request.Priority priority) {
        this.mResponseClass = clazz;
        this.mUri = uri;
        this.mPriority = priority == null
                ? Request.Priority.NORMAL
                : priority;
    }

    public Class<? extends ParcelableApiResponse> getResponseClass() {
        return mResponseClass;
    }

    public Uri getUri() {
        return mUri;
    }

    public Request.Priority getPriority() {
        return mPriority;
    }

    public static class Response {
        public SaveableApiResponse mResponse;

        public Response(SaveableApiResponse response) {
            this.mResponse = response;
        }

        public SaveableApiResponse getResponse() {
            return mResponse;
        }
    }
}
