package com.pissiphany.matterexplorer.rx.observable;

import android.support.annotation.NonNull;

import java.io.File;

import rx.Single;
import rx.SingleSubscriber;

/**
 * Created by kierse on 16-02-07.
 */
public class FileProcessing {
    private FileProcessing() {
        throw new AssertionError();
    }

    public static Single<Boolean> buildPreviewsOfFile(
            @NonNull final File source,
            @NonNull final File landscapeDestination,
            @NonNull final File portraitDestination
    ) {
        return Single.create(new Single.OnSubscribe<Boolean>() {
            @Override
            public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
                // TODO generate landscape bitmap preview of souce
                // TODO generate portrait bitmap preview of souce

                // TODO save generated Bitmap to disk at landscapeDestination
                // TODO save generated Bitmap to disk at portraitDestination

                if (landscapeDestination.exists() && landscapeDestination.isFile()
                        && portraitDestination.exists() && portraitDestination.isFile()) {
                    singleSubscriber.onSuccess(true);
                } else {
                    singleSubscriber.onError(new Throwable("" /* TODO throw real error */));
                }
            }
        });
    }
}
