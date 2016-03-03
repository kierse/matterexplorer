package com.pissiphany.matterexplorer.view.ui;

import android.app.Application;
import android.net.Uri;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.pissiphany.matterexplorer.App;
import com.pissiphany.matterexplorer.old.rx.RxBus;
import com.pissiphany.matterexplorer.model.network.NetworkEventHandler;

import javax.inject.Named;
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
    void inject(App app);

    Application application();
    RequestQueue requestQueue();
    NetworkEventHandler networkEventHandler();
    RxBus rxBus();
    Gson gson();

    @Named("api_root")
    Uri apiRoot();

    @Named("api_token")
    String apiToken();
}
