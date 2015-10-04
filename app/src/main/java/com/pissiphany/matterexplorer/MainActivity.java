package com.pissiphany.matterexplorer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.pissiphany.matterexplorer.db.event.DatabaseServiceEvent;
import com.pissiphany.matterexplorer.di.component.ActivityComponent;
import com.pissiphany.matterexplorer.di.HasComponent;
import com.pissiphany.matterexplorer.di.component.DaggerActivityComponent;
import com.pissiphany.matterexplorer.di.module.ActivityModule;
import com.pissiphany.matterexplorer.network.api.themis.contract.ThemisContractV2;
import com.pissiphany.matterexplorer.network.api.themis.response.MatterResponseV2;
import com.pissiphany.matterexplorer.network.event.GetAndSaveEvent;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements HasComponent<ActivityComponent> {
    private static final String FETCH_DATA_KEY = "fetch_data";

    private ActivityComponent mComponent;

    @Inject
    RxBus sBus;

    private boolean mFetchData = false;
    private Subscription mEventSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        mComponent.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mEventSubscription = sBus.toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (!(o instanceof DatabaseServiceEvent)) return;
            }
        });

        if (!mFetchData) {
            sBus.send(new GetAndSaveEvent(
                    MatterResponseV2.class,
                    ThemisContractV2.getUriForEndpoint(ThemisContractV2.Endpoints.MATTERS),
                    Request.Priority.IMMEDIATE
            ));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mEventSubscription.unsubscribe();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putBoolean(FETCH_DATA_KEY, mFetchData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mFetchData = savedInstanceState.getBoolean(FETCH_DATA_KEY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public ActivityComponent getComponent() {
        return this.mComponent;
    }
}
