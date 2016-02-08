package com.pissiphany.matterexplorer.rx.observable;

import android.net.Uri;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.RequestFuture;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.pissiphany.matterexplorer.network.api.ParcelableApiResponse;
import com.pissiphany.matterexplorer.network.api.themis.contract.ThemisContractV2;
import com.pissiphany.matterexplorer.network.api.themis.response.BaseResponseV2;
import com.pissiphany.matterexplorer.network.request.AuthenticatedGsonRequest;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by kierse on 16-02-07.
 */
public class ApiEndpoint {
    private ApiEndpoint() {
        throw new AssertionError();
    }


    public static Observable<ParcelableApiResponse> callEndpoint(
            Gson gson,
            RequestQueue queue,
            ThemisContractV2.Endpoints endpoint,
            Uri root,
            Class<? extends ParcelableApiResponse> klass,
            String apiToken
    ) {
        RequestFuture<ParcelableApiResponse> future = RequestFuture.newFuture();

        ImmutableMap<String, String> headers = new ImmutableMap.Builder<String, String>()
                .putAll(ThemisContractV2.DEFAULT_HEADERS)
                .put(ThemisContractV2.getAuthorizationHeader(apiToken))
                .build();

        AuthenticatedGsonRequest<ParcelableApiResponse> request =
                new AuthenticatedGsonRequest.Builder<>(
                            gson,
                            Request.Method.GET,
                            ThemisContractV2.getUriForEndpoint(root, endpoint).toString(),
                            klass,
                            future,
                            future)
                        .setPriority(Request.Priority.IMMEDIATE)
                        .setHeaders(headers)
                        .build();

        queue.add(request);

        return Observable.from(future, Schedulers.io());
    }
}
