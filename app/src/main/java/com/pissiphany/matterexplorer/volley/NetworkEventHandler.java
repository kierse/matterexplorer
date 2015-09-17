package com.pissiphany.matterexplorer.volley;

import com.pissiphany.matterexplorer.RxBus;
import com.pissiphany.matterexplorer.volley.event.FetchAndSaveEvent;
import com.pissiphany.matterexplorer.volley.event.NetworkEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action1;

/**
 * Created by kierse on 15-09-13.
 */
@Singleton
public class NetworkEventHandler {
    private RxBus sBus;

    @Inject
    public NetworkEventHandler(RxBus bus) {
        this.sBus = bus;

        sBus.toObservable()
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object event) {
                        if (!(event instanceof NetworkEvent)) return;

                        if (event instanceof FetchAndSaveEvent) handleFetchAndSave(event);
                    }
                });
    }

    private void handleFetchAndSave(Object event) {

    }
}
