package com.pissiphany.matterexplorer.rx.observable;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.google.common.collect.ImmutableMap;
import com.pissiphany.matterexplorer.network.api.themis.contract.ThemisContractV2;
import com.pissiphany.matterexplorer.network.request.AuthenticatedStringRequest;

import java.io.File;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kierse on 16-02-07.
 */
public class DownloadFile {
    private DownloadFile() {
        throw new AssertionError();
    }

    public static Single<Uri> fetchDownloadUri(
            final RequestQueue queue,
            final String apiToken,
            @NonNull final Uri apiUri
    ) {
        ImmutableMap<String, String> headers = new ImmutableMap.Builder<String, String>()
                .putAll(ThemisContractV2.DEFAULT_HEADERS)
                .put(ThemisContractV2.getAuthorizationHeader(apiToken))
                .build();

        RequestFuture<String> future = RequestFuture.newFuture();
        queue.add(new AuthenticatedStringRequest(
                Request.Method.HEAD,
                apiUri.toString(),
                headers,
                Request.Priority.IMMEDIATE,
                future,
                future
        ));

        return Single.from(future, Schedulers.io())
                .onErrorReturn(new Func1<Throwable, String>() {
                    @Override
                    public String call(Throwable throwable) {
                        ServerError error = ((ServerError) ((ExecutionException) throwable).getCause());
                        if (error != null
                                && error.networkResponse != null
                                && error.networkResponse.headers != null) {
                            String location = error.networkResponse.headers.get("Location");
                            if (location != null
                                    && location.length() > 0
                                    && error.networkResponse.statusCode == HttpURLConnection.HTTP_SEE_OTHER) {
                                return location;
                            }
                        }

                        return "";
                    }
                })
                .flatMap(new Func1<String, Single<? extends Uri>>() {
                    @Override
                    public Single<? extends Uri> call(String s) {
                        if (s.isEmpty()) {
                            return Single.error(new IllegalArgumentException("empty download location"));
                        } else {
                            return Single.just(Uri.parse(s));
                        }
                    }
                })
                .delay(3, TimeUnit.SECONDS);
    }

    public static Single<File> downloadFile(@NonNull final Uri remoteUri, @NonNull final File saveTo) {
        return Single.defer(new Callable<Single<File>>() {
            @Override
            public Single<File> call() throws Exception {
                return Single.just(saveTo);
            }
        }).delay(5, TimeUnit.SECONDS);
    }
}
