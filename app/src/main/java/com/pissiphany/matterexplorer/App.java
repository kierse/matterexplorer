package com.pissiphany.matterexplorer;

import android.app.Application;

import com.pissiphany.matterexplorer.di.component.ApplicationComponent;
import com.pissiphany.matterexplorer.di.component.DaggerApplicationComponent;
import com.pissiphany.matterexplorer.di.module.ApplicationModule;

/**
 * Created by kierse on 15-09-07.
 */
public class App extends Application {
    private ApplicationComponent sComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.sComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        this.sComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return sComponent;
    }
}
