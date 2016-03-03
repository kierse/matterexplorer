package com.pissiphany.matterexplorer.view.ui;

import android.app.Application;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pissiphany.matterexplorer.old.rx.RxBus;
import com.pissiphany.matterexplorer.model.gson.AutoParcelTypeAdapterFactory;
import com.pissiphany.matterexplorer.model.gson.DateTimeTypeAdapter;
import com.pissiphany.matterexplorer.model.gson.LocalDateTypeAdapter;
import com.pissiphany.matterexplorer.model.network.NetworkEventHandler;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kierse on 15-09-07.
 */
@Module
public final class ApplicationModule {
    private final Application mApplication;
    private final Uri mRootUri;
    private final String mApiToken;

    public ApplicationModule(Application app, Uri rootUri, String apiToken) {
        this.mApplication = app;
        this.mRootUri = rootUri;
        this.mApiToken = apiToken;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Named("api_root")
    Uri provideRootUri() {
        return mRootUri;
    }

    @Provides
    @Named("api_token")
    String provideApiToken() {
        return mApiToken;
    }

    @Provides
    @Singleton
    RequestQueue provideRequestQueue(Application application) {
        return Volley.newRequestQueue(application, new HurlStack() {
            @Override
            protected HttpURLConnection createConnection(URL url) throws IOException {
                HttpURLConnection connection = super.createConnection(url);
                connection.setInstanceFollowRedirects(false);

                return connection;
            }
        });
    }

    @Provides
    @Singleton
    NetworkEventHandler providesNetworkEventHandler(
            Application application,
            RxBus bus,
            RequestQueue queue,
            Gson gson)
    {
        return new NetworkEventHandler(application, bus, queue, gson, mApiToken);
    }

    @Provides
    Gson providesGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new AutoParcelTypeAdapterFactory())
                .registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
                .registerTypeAdapter(DateTime.class, new DateTimeTypeAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
