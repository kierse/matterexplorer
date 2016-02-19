package com.pissiphany.matterexplorer.di.component;

import android.app.Activity;

import com.pissiphany.matterexplorer.annotation.PerActivity;

import com.pissiphany.matterexplorer.di.module.ActivityModule;
import com.pissiphany.matterexplorer.rx.RxMainActivityContainer;
import com.pissiphany.matterexplorer.ui.matter.MatterActivity;
import com.pissiphany.matterexplorer.ui.rx.RxMainActivity;

import dagger.Component;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by kierse on 15-09-12.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MatterActivity activity);
    void inject(RxMainActivity activity);

    Activity activity();

    RxMainActivityContainer rxContainer();

    CompositeSubscription compositeSubscription();
}
