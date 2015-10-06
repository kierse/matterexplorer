package com.pissiphany.matterexplorer.di.module;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pissiphany.matterexplorer.RxBus;
import com.pissiphany.matterexplorer.gson.AutoParcelTypeAdapterFactory;
import com.pissiphany.matterexplorer.gson.DateTimeTypeAdapter;
import com.pissiphany.matterexplorer.gson.LocalDateTypeAdapter;
import com.pissiphany.matterexplorer.network.NetworkEventHandler;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kierse on 15-09-07.
 */
@Module
public final class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application app) {
        this.mApplication = app;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    RequestQueue provideRequestQueue(Application application) {
        return Volley.newRequestQueue(application);
    }

    @Provides
    @Singleton
    NetworkEventHandler providesNetworkEventHandler(Application application, RxBus bus, RequestQueue queue, Gson gson) {
        return new NetworkEventHandler(application, bus, queue, gson);
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
