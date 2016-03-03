package com.pissiphany.matterexplorer.view.ui;

import android.support.v7.app.AppCompatActivity;

import com.pissiphany.matterexplorer.App;

/**
 * Created by kierse on 15-09-12.
 */
public class BaseActivity extends AppCompatActivity {
    protected ApplicationComponent getApplicationComponent() {
        return ((App) getApplicationContext()).getComponent();
    }
}
