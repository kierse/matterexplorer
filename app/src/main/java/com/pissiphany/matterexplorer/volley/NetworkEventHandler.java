package com.pissiphany.matterexplorer.volley;

import android.app.Application;
import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.pissiphany.matterexplorer.RxBus;
import com.pissiphany.matterexplorer.service.DatabaseService;
import com.pissiphany.matterexplorer.volley.event.GetAndSaveEvent;
import com.pissiphany.matterexplorer.volley.event.NetworkEvent;
import com.pissiphany.matterexplorer.volley.request.AuthenticatedGsonRequest;
import com.pissiphany.matterexplorer.volley.response.ParcelableApiResponse;

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
    private Application sApp;
    private RxBus sBus;
    private RequestQueue sQueue;

    @Inject
    public NetworkEventHandler(Application application, RxBus bus, RequestQueue queue) {
        this.sApp = application;
        this.sBus = bus;
        this.sQueue = queue;

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
        AuthenticatedGsonRequest<ParcelableApiResponse> request = new AuthenticatedGsonRequest<>(
                Request.Method.GET,
                event.getUri().toString(),
                event.getResponseClass(),
                this, // Response.Listener
                this  // Response.ErrorListener
        );

        request.setPriority(event.getPriority());

//        AuthenticatedGsonRequest<SaveableApiResponse> request = new AuthenticatedGsonRequest.Builder<>(
//                    Request.Method.GET,
//                    event.getUri().toString(),
//                    event.getResponseClass(),
//                    this, // Response.Listener
//                    this // Response.ErrorListener
//        ).setPriority(event.getPriority()).build();

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
