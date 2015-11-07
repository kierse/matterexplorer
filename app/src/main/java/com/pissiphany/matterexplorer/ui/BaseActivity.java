package com.pissiphany.matterexplorer.ui;

import android.support.v7.app.AppCompatActivity;

import com.pissiphany.matterexplorer.App;
import com.pissiphany.matterexplorer.di.component.ApplicationComponent;

/**
 * Created by kierse on 15-09-12.
 */
public class BaseActivity extends AppCompatActivity {
    protected ApplicationComponent getApplicationComponent() {
        return ((App) getApplicationContext()).getComponent();
    }
}
