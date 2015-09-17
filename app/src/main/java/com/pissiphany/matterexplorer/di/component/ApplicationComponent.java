package com.pissiphany.matterexplorer.di.component;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.pissiphany.matterexplorer.RxBus;
import com.pissiphany.matterexplorer.di.module.ApplicationModule;
import com.pissiphany.matterexplorer.volley.NetworkEventHandler;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kierse on 15-09-12.
 *
 * http://fernandocejas.com/2015/04/11/tasting-dagger-2-on-android/
 * http://code.tutsplus.com/tutorials/dependency-injection-with-dagger-2-on-android--cms-23345
 * https://guides.codepath.com/android/Dependency-Injection-with-Dagger-2
 * http://stackoverflow.com/questions/28170292/problems-with-singletons-when-using-component-dependencies
 * https://www.reddit.com/r/androiddev/comments/3ecwly/struggling_to_understand_dagger2_please_help/
 * https://github.com/frogermcs/GithubClient
 * https://github.com/gk5885/dagger-android-sample
 * https://github.com/android10/Android-CleanArchitecture
 *
 */
@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    void inject(Application application);

    Application application();
    RequestQueue requestQueue();
    NetworkEventHandler networkEventHandler();
    RxBus rxBus();
}
