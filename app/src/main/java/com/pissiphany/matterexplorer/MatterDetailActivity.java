package com.pissiphany.matterexplorer;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.pissiphany.matterexplorer.model.Matter;
import com.pissiphany.matterexplorer.provider.contract.MatterContract;

/**
 * Created by kierse on 15-10-05.
 */
public class MatterDetailActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String MATTER_ID_KEY = "matter_id";
    private static final String MATTER = "matter";
    private static final int MATTER_LOADER_ID = 0;

    private static final String[] PROJECTION = new String[] {
            MatterContract.Columns.DISPLAY_NUMBER,
            MatterContract.Columns.DESCRIPTION,
            MatterContract.Columns.PENDING_DATE,
            MatterContract.Columns.OPEN_DATE,
            MatterContract.Columns.CLOSE_DATE,
            MatterContract.Columns.STATUS,
            MatterContract.Columns.BILLING_METHOD,
            MatterContract.Columns.BILLABLE,
    };

    private Matter mMatter;

    public static Intent newIntent(Context context, long matterId) {
        Intent intent = new Intent(context, MatterDetailActivity.class);
        intent.putExtra(MATTER_ID_KEY, matterId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matter_detail_activity);

        if (savedInstanceState != null && savedInstanceState.containsKey(MATTER)) {
            mMatter = savedInstanceState.getParcelable(MATTER);
            displayMatter(mMatter);
        } else {
            Bundle args = new Bundle();
            args.putLong(MATTER_ID_KEY, getIntent().getLongExtra(MATTER_ID_KEY, -1));
            getLoaderManager().initLoader(MATTER_LOADER_ID, args, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        long matterId = args.getLong(MATTER_ID_KEY, -1);
        if (matterId > 0) {
            return new CursorLoader(this,
                    MatterContract.buildSelectionUri(matterId), PROJECTION, null, null, null);
        }

        return null;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 1 && data.moveToFirst()) {
            mMatter = Matter.builder().fromCursor(data).build();
            displayMatter(mMatter);
        }
    }

    private void displayMatter(Matter matter) {
        ((TextView) findViewById(R.id.display_number)).setText(matter.getDisplayNumber());
        ((TextView) findViewById(R.id.description)).setText(matter.getDescription());
        ((TextView) findViewById(R.id.billing_method)).setText(matter.getBillingMethod());

        if (matter.getPendingDate() != null) {
            ((TextView) findViewById(R.id.pending_date)).setText(matter.getPendingDate().toString());
        }
        if (matter.getOpenDate() != null) {
            ((TextView) findViewById(R.id.open_date)).setText(matter.getOpenDate().toString());
        }
        if (matter.getCloseDate() != null) {
            ((TextView) findViewById(R.id.closed_date)).setText(matter.getCloseDate().toString());
        }
        if (matter.getBillable() != null) {
            ((Switch) findViewById(R.id.billable)).setChecked(matter.getBillable());
        }
    }
}
