package com.pissiphany.matterexplorer.ui.matter;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.pissiphany.matterexplorer.R;
import com.pissiphany.matterexplorer.adapter.MatterListCursorAdapter;
import com.pissiphany.matterexplorer.db.event.DatabaseServiceEvent;
import com.pissiphany.matterexplorer.di.HasComponent;
import com.pissiphany.matterexplorer.di.component.ActivityComponent;
import com.pissiphany.matterexplorer.di.component.DaggerActivityComponent;
import com.pissiphany.matterexplorer.di.module.ActivityModule;
import com.pissiphany.matterexplorer.network.api.themis.contract.ThemisContractV2;
import com.pissiphany.matterexplorer.network.api.themis.response.MatterResponseV2;
import com.pissiphany.matterexplorer.network.event.GetAndSaveEvent;
import com.pissiphany.matterexplorer.provider.contract.MatterContract;
import com.pissiphany.matterexplorer.rx.RxBus;
import com.pissiphany.matterexplorer.ui.BaseActivity;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MatterActivity extends BaseActivity implements
        HasComponent<ActivityComponent>,
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {
    private static final String FETCH_DATA_KEY = "fetch_data";
    private static final int MATTERS_LOADER_ID = 0;

    private static final String[] PROJECTION = new String[] {
            MatterContract.Columns._ID,
            MatterContract.Columns.ID,
            MatterContract.Columns.DISPLAY_NUMBER,
            MatterContract.Columns.DESCRIPTION,
            MatterContract.Columns.STATUS,
    };

    private ActivityComponent mComponent;
    private MatterListCursorAdapter mAdapter;

    @Inject
    RxBus sBus;

    @Inject
    @Named("api_root")
    Uri mRootUri;

    private boolean mFetchData = false;
    private Subscription mEventSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matter_activity);

        mComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        mComponent.inject(this);

        mAdapter = new MatterListCursorAdapter(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(this);

        getLoaderManager().initLoader(MATTERS_LOADER_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mEventSubscription = sBus.toObservable()
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object o) {
                        return o instanceof DatabaseServiceEvent;
                    }
                })
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        onEvent((DatabaseServiceEvent) o);
                    }
                });

        if (!mFetchData) {
            sBus.send(new GetAndSaveEvent(
                    MatterResponseV2.class,
                    ThemisContractV2.getUriForEndpoint(mRootUri, ThemisContractV2.Endpoints.MATTERS),
                    Request.Priority.IMMEDIATE
            ));
            mFetchData = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mEventSubscription.unsubscribe();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) mAdapter.getItem(position);
        Intent intent = MatterDetailActivity
                .newIntent(this, cursor.getLong(cursor.getColumnIndex(MatterContract.Columns.ID)));
        startActivity(intent);
    }

    @Override
    public ActivityComponent getComponent() {
        return this.mComponent;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MatterContract.CONTENT_URI, PROJECTION, null, null, null);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    private void onEvent(DatabaseServiceEvent event) {
        if (event.getUpsertCount() > 0) {
            Toast toast = Toast.makeText(
                    this,
                    String.format(getResources().getString(R.string.get_and_save_result), event.getUpsertCount()),
                    Toast.LENGTH_SHORT
            );

            toast.show();
        }
    }
}
