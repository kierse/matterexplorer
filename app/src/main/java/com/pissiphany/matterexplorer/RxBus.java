package com.pissiphany.matterexplorer;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by kierse on 15-09-13.
 *
 * Based on code samples and tutorial at:
 * http://nerds.weddingpartyapp.com/tech/2014/12/24/implementing-an-event-bus-with-rxjava-rxbus/
 * https://gist.github.com/benjchristensen/04eef9ca0851f3a5d7bf
 */
@Singleton
public class RxBus {
    private final Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());

    @Inject
    public RxBus() { }

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}
