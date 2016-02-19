package com.pissiphany.matterexplorer.di.module;

import android.app.Activity;

import com.pissiphany.matterexplorer.rx.RxMainActivityContainer;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

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

    @Provides
    RxMainActivityContainer providesRxMainActivityContainer() {
        return new RxMainActivityContainer();
    }

    @Provides
    CompositeSubscription providesCompositeSubscription() {
        return new CompositeSubscription();
    }
}
