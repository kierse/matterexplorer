package com.pissiphany.matterexplorer.di.component;

import com.pissiphany.matterexplorer.annotation.PerIntentService;
import com.pissiphany.matterexplorer.service.DatabaseService;

import dagger.Component;

/**
 * Created by kierse on 15-10-03.
 */
@PerIntentService
@Component(dependencies = {ApplicationComponent.class})
public interface IntentServiceComponent {
    void inject(DatabaseService service);
}
