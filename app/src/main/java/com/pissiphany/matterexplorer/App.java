package com.pissiphany.matterexplorer;

import android.app.Application;
import android.net.Uri;

import com.pissiphany.matterexplorer.di.HasComponent;
import com.pissiphany.matterexplorer.di.component.ApplicationComponent;
import com.pissiphany.matterexplorer.di.component.DaggerApplicationComponent;
import com.pissiphany.matterexplorer.di.module.ApplicationModule;
import com.pissiphany.matterexplorer.network.NetworkEventHandler;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

/**
 * Created by kierse on 15-09-07.
 */
public class App extends Application implements HasComponent<ApplicationComponent> {
    private ApplicationComponent sComponent;

    // Injecting an instance forces Dagger to create the singleton which allows it to register
    // itself with the event bus
    @Inject
    NetworkEventHandler sNetworkEventHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * Note: a resource file must exist at res/values/themis_api.xml and contain the following info:
         *
         * <resources>
         *     <string name="scheme">https</string>
         *     <string name="authority">some.url.com</string>
         *     <string name="token">oauth token value</string>
         * </resources>
         *
         */
        Uri rootUri = buildRootUri();
        String token = getResources().getString(R.string.token);

        this.sComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, rootUri, token))
                .build();
        this.sComponent.inject(this);

        // initialize JodaTimeAndroid
        JodaTimeAndroid.init(this);
    }

    @Override
    public ApplicationComponent getComponent() {
        return sComponent;
    }

    private Uri buildRootUri() {
        return new Uri.Builder()
                .scheme(getResources().getString(R.string.scheme))
                .authority(getResources().getString(R.string.authority))
                .build();
    }
}
