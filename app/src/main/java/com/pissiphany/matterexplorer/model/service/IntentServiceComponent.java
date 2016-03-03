package com.pissiphany.matterexplorer.model.service;

import com.pissiphany.matterexplorer.di.annotation.PerIntentService;
import com.pissiphany.matterexplorer.model.service.DatabaseService;
import com.pissiphany.matterexplorer.view.ui.ApplicationComponent;

import dagger.Component;

/**
 * Created by kierse on 15-10-03.
 */
@PerIntentService
@Component(dependencies = {ApplicationComponent.class})
public interface IntentServiceComponent {
    void inject(DatabaseService service);
}
