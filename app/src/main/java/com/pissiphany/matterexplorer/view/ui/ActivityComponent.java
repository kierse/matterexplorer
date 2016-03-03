package com.pissiphany.matterexplorer.view.ui;

import android.app.Activity;

import com.pissiphany.matterexplorer.di.annotation.PerActivity;

import com.pissiphany.matterexplorer.old.rx.RxMainActivityContainer;
import com.pissiphany.matterexplorer.view.ui.matter.MatterActivity;
import com.pissiphany.matterexplorer.view.ui.rx.RxMainActivity;

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
