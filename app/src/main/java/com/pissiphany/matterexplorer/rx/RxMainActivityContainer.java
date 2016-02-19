package com.pissiphany.matterexplorer.rx;

import android.net.Uri;

import com.android.volley.RequestQueue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.pissiphany.matterexplorer.rx.observable.DownloadFile.downloadFile;
import static com.pissiphany.matterexplorer.rx.observable.DownloadFile.fetchDownloadUri;

/**
 * Created by kierse on 16-02-17.
 */
public class RxMainActivityContainer {
    private Single<File> mActiveDownload1Single;
    private Single<File> mActiveDownload2Single;
    private Single<File> mActiveDownload3Single;

    @Inject
    public RxMainActivityContainer() { }

    public Single<File> buildDownload1Single(Uri remoteUri, File saveTo) {
        mActiveDownload1Single = downloadFile(remoteUri, saveTo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                // rx.Single doesn't seem to have a cache() or replay() method (as of v1.1.1)
                // In order to get this functionality I have to convert it to an Observable,
                // call #cache, then back to a Single
                .toObservable()
                .cache()

                // #toSingle converts the Observable to a Single taking only the first emitted
                // value. If the Observable were to emit more than one value it would throw an
                // exception.
                .toSingle();

        return mActiveDownload1Single;
    }

    public Single<File> getDownload1Single() {
        return mActiveDownload1Single;
    }

    public Single<File> buildDownload2Single(
            RequestQueue queue,
            String token,
            Uri remoteUri,
            final File saveTo
    ) {
        mActiveDownload2Single = fetchDownloadUri(queue, token, remoteUri)
                .flatMap(new Func1<Uri, Single<File>>() {
                     @Override
                     public Single<File> call(Uri downloadUri) {
                        return downloadFile(downloadUri, saveTo);
                }
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                // rx.Single doesn't seem to have a cache() or replay() method (as of v1.1.1)
                // In order to get this functionality I have to convert it to an Observable,
                // call #cache, then back to a Single
                .toObservable()
                .cache()

                // #toSingle converts the Observable to a Single taking only the first emitted
                // value. If the Observable were to emit more than one value it would throw an
                // exception.
                .toSingle();

        return mActiveDownload2Single;
    }

    public Single<File> getDownload2Single() {
        return mActiveDownload2Single;
    }
}
