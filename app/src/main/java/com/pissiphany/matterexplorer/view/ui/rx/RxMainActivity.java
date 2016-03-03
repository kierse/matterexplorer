package com.pissiphany.matterexplorer.view.ui.rx;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.pissiphany.matterexplorer.R;
import com.pissiphany.matterexplorer.di.HasComponent;
import com.pissiphany.matterexplorer.view.ui.ActivityComponent;
import com.pissiphany.matterexplorer.view.ui.ActivityModule;
import com.pissiphany.matterexplorer.model.network.api.themis.contract.ThemisContractV2;
import com.pissiphany.matterexplorer.old.rx.RxMainActivityContainer;
import com.pissiphany.matterexplorer.view.ui.BaseActivity;
import com.pissiphany.matterexplorer.view.ui.DaggerActivityComponent;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxMainActivity extends BaseActivity implements
        HasComponent<ActivityComponent> {
    private static final String AVATARS_CACHE = "avatars";
    private static final String BILLS_CACHE = "bills";

    private ActivityComponent mComponent;
    private RxMainActivityContainer mRxContainer;

    @Inject
    RequestQueue mQueue;

    @Inject
    Gson mGson;

    @Inject
    @Named("api_root")
    Uri mRootUri;

    @Inject
    @Named("api_token")
    String mApiToken;

    @Inject
    CompositeSubscription mSubscriptions;

    @Bind(R.id.download_file_1)
    Button mDownloadFile1;

    @Bind(R.id.download_file_2)
    Button mDownloadFile2;

    @Bind(R.id.download_file_3)
    Button mDownloadFile3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.rx_main_activity);
        ButterKnife.bind(this);

        mComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
        mComponent.inject(this);

        if (savedInstanceState == null) {
            mRxContainer = mComponent.rxContainer();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mRxContainer == null) {
            mRxContainer = (RxMainActivityContainer) getLastCustomNonConfigurationInstance();

            // one
            Single<File> one = mRxContainer.getDownload1Single();
            if (one != null) mSubscriptions.add(subscribeToDownloadFile1(one));

            // two
            Single<File> two = mRxContainer.getDownload2Single();
            if (two != null) mSubscriptions.add(subscribeToDownloadFile2(two));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mSubscriptions.unsubscribe();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mRxContainer;
    }

    @Override
    public ActivityComponent getComponent() {
        return this.mComponent;
    }

    @OnClick(R.id.download_file_1)
    void onClickDownloadFile1() {
        long billId = 1L;
        Uri remoteUri = ThemisContractV2.getUriForEndpoint(
                mRootUri,
                ThemisContractV2.Endpoints.BILL_DOWNLOAD,
                billId
        );
        File saveTo = new File(this.getCacheDir(), String.format("%s/%d/bill.pdf", BILLS_CACHE, billId));

        mSubscriptions.add(
                subscribeToDownloadFile1(mRxContainer.buildDownload1Single(remoteUri, saveTo))
        );
    }

    @OnClick(R.id.download_file_2)
    void onClickDownloadFile2() {
        long userId = 344214492L;
        Uri remoteUri = ThemisContractV2.getUriForEndpoint(
                mRootUri,
//                Uri.parse("http://192.168.56.1:3000"),
                ThemisContractV2.Endpoints.USER_AVATAR_DOWNLOAD,
                userId
        );
        final File saveTo = new File(
                this.getCacheDir(),
                String.format("%s/%d/avatar.png", AVATARS_CACHE, userId)
        );

        mSubscriptions.add(subscribeToDownloadFile2(
                        mRxContainer.buildDownload2Single(mQueue, mApiToken, remoteUri, saveTo)
        ));
    }

    @OnClick(R.id.download_file_3)
    void onClickDownloadFile3() {

    }

    private Subscription subscribeToDownloadFile1(Single<File> single) {
        return single.subscribe(new SingleSubscriber<File>() {
            @Override
            public void onSuccess(File value) {
                Toast.makeText(RxMainActivity.this, "file 1 downloaded!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(RxMainActivity.this, "error downloading file 1!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Subscription subscribeToDownloadFile2(Single<File> single) {
        return single.subscribe(new SingleSubscriber<File>() {
            @Override
            public void onSuccess(File value) {
                Toast.makeText(RxMainActivity.this, "file 2 downloaded!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Throwable error) {
                Toast.makeText(RxMainActivity.this, "error downloading file 2!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
