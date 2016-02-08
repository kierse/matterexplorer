package com.pissiphany.matterexplorer.di.component;

import android.app.Activity;

import com.pissiphany.matterexplorer.annotation.PerActivity;

import com.pissiphany.matterexplorer.di.module.ActivityModule;
import com.pissiphany.matterexplorer.ui.matter.MatterActivity;

import dagger.Component;

/**
 * Created by kierse on 15-09-12.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MatterActivity activity);

    Activity activity();
}
