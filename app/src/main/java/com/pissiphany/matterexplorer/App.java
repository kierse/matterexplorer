package com.pissiphany.matterexplorer;

import android.app.Application;

import com.pissiphany.matterexplorer.di.HasComponent;
import com.pissiphany.matterexplorer.di.component.ApplicationComponent;
import com.pissiphany.matterexplorer.di.component.DaggerApplicationComponent;
import com.pissiphany.matterexplorer.di.module.ApplicationModule;
import com.pissiphany.matterexplorer.network.NetworkEventHandler;

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

        this.sComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        this.sComponent.inject(this);
    }

    @Override
    public ApplicationComponent getComponent() {
        return sComponent;
    }
}
