package com.pissiphany.matterexplorer.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kierse on 15-10-04.
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }
}
