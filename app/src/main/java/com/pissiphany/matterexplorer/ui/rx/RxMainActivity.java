package com.pissiphany.matterexplorer.ui.rx;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;
import com.pissiphany.matterexplorer.R;
import com.pissiphany.matterexplorer.di.HasComponent;
import com.pissiphany.matterexplorer.di.component.ActivityComponent;
import com.pissiphany.matterexplorer.di.component.DaggerActivityComponent;
import com.pissiphany.matterexplorer.di.module.ActivityModule;
import com.pissiphany.matterexplorer.network.api.themis.contract.ThemisContractV2;
import com.pissiphany.matterexplorer.ui.BaseActivity;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Single;
import rx.SingleSubscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.pissiphany.matterexplorer.rx.observable.DownloadFile.downloadFile;
import static com.pissiphany.matterexplorer.rx.observable.DownloadFile.fetchDownloadUri;

public class RxMainActivity extends BaseActivity implements
        HasComponent<ActivityComponent> {
    private static final String AVATARS_CACHE = "avatars";
    private static final String BILLS_CACHE = "bills";

    private ActivityComponent mComponent;

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
    }

    @Override
    protected void onResume() {
        super.onResume();

//        fetchMatters()
//                .map(new Func1<ParcelableApiResponse, Object>() {
//                    @Override
//                    public Object call(ParcelableApiResponse parcelableApiResponse) {
//                        List<PersistableParent> persistableParents = parcelableApiResponse.getPersistableParents();
//                        if (!persistableParents.isEmpty()) {
//
//                        }
//
//                        return null;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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

        downloadFile(remoteUri, saveTo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<File>() {
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

//       fetchDownloadUri(mQueue, "token", remoteUri)
        fetchDownloadUri(mQueue, mApiToken, remoteUri)
               .flatMap(new Func1<Uri, Single<File>>() {
                   @Override
                   public Single<File> call(Uri downloadUri) {
                       return downloadFile(downloadUri, saveTo);
                   }
               })
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new SingleSubscriber<File>() {
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

    @OnClick(R.id.download_file_3)
    void onClickDownloadFile3() {

    }

//    private void test() {
//        final Uri apiUri = Uri.EMPTY;
//
//        final File saveTo = null;
//        final File landscape = null;
//        final File portrait = null;
//
//        fetchDownloadUri(apiUri)
//                .flatMap(new Func1<Uri, Single<File>>() {
//                    @Override
//                    public Single<File> call(Uri remoteUri) {
//                        return downloadFile(remoteUri, saveTo);
//                    }
//                })
//                .flatMap(new Func1<File, Single<Boolean>>() {
//                    @Override
//                    public Single<Boolean> call(File source) {
//                        return buildPreviewsOfFile(source, landscape, portrait);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new SingleSubscriber<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean value) {
//                        displayPreview();
//                    }
//
//                    @Override
//                    public void onError(Throwable error) {
//                        // TODO do something with error
//                    }
//                });
//    }
//
//    private void displayPreview() { }
}
