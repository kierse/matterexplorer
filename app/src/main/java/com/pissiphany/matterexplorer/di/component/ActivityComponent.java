package com.pissiphany.matterexplorer.di.component;

import com.pissiphany.matterexplorer.MainActivity;
import com.pissiphany.matterexplorer.annotation.PerActivity;

import dagger.Component;

/**
 * Created by kierse on 15-09-12.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class})
public interface ActivityComponent {
    void inject(MainActivity activity);
}
