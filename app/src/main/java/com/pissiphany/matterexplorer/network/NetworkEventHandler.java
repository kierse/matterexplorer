package com.pissiphany.matterexplorer.network;

import android.app.Application;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.pissiphany.matterexplorer.RxBus;
import com.pissiphany.matterexplorer.network.api.themis.contract.ThemisContractV2;
import com.pissiphany.matterexplorer.network.request.AuthenticatedGsonRequest;
import com.pissiphany.matterexplorer.service.DatabaseService;
import com.pissiphany.matterexplorer.network.event.GetAndSaveEvent;
import com.pissiphany.matterexplorer.network.event.NetworkEvent;
import com.pissiphany.matterexplorer.network.api.ParcelableApiResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

/**
 * Created by kierse on 15-09-13.
 */
@Singleton
public class NetworkEventHandler implements
        Response.Listener<ParcelableApiResponse>,
        Response.ErrorListener {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_VALUE = "Bearer ";

    private Application sApp;
    private RxBus sBus;
    private RequestQueue sQueue;
    private Gson mGson;
    private String mApiToken;
    private ImmutableMap<String, String> mHeaders;

    @Inject
    public NetworkEventHandler(
            Application application,
            RxBus bus,
            RequestQueue queue,
            Gson gson,
            String apiToken)
    {
        this.sApp = application;
        this.sBus = bus;
        this.sQueue = queue;
        this.mGson = gson;
        this.mApiToken = apiToken;

        mHeaders = new ImmutableMap.Builder<String, String>()
                .putAll(ThemisContractV2.DEFAULT_HEADERS)
                .put(AUTHORIZATION_HEADER, AUTHORIZATION_VALUE + mApiToken)
                .build();

        sBus.toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (!(event instanceof NetworkEvent)) return;

                        if (event instanceof GetAndSaveEvent) {
                            onEvent((GetAndSaveEvent) event);
                        }
                    }
                });
    }

    private void onEvent(GetAndSaveEvent event) {
        AuthenticatedGsonRequest<ParcelableApiResponse> request =
                new AuthenticatedGsonRequest.Builder<ParcelableApiResponse>(mGson)
                        .setMethod(Request.Method.GET)
                        .setUrl(event.getUri().toString())
                        .setResponseClass(event.getResponseClass())
                        .setListener(this)
                        .setErrorListener(this)
                        .setPriority(event.getPriority())
                        .setHeaders(mHeaders)
                        .build();

        sQueue.add(request);
    }

    @Override
    public void onResponse(ParcelableApiResponse response) {
        Intent service = new Intent(sApp, DatabaseService.class);
        service.putExtra(DatabaseService.API_RESPONSE_OBJECT, response);
        sApp.startService(service);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        // TODO
    }
}
