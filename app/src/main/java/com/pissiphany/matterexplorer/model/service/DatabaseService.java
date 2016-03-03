package com.pissiphany.matterexplorer.model.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.pissiphany.matterexplorer.App;
import com.pissiphany.matterexplorer.old.rx.RxBus;
import com.pissiphany.matterexplorer.model.db.DeleteDirector;
import com.pissiphany.matterexplorer.model.db.UpsertDirector;
import com.pissiphany.matterexplorer.model.db.event.DatabaseServiceEvent;
import com.pissiphany.matterexplorer.model.model.PersistableParent;
import com.pissiphany.matterexplorer.model.provider.contract.BaseContract;
import com.pissiphany.matterexplorer.model.network.api.ParcelableApiResponse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by kierse on 15-09-18.
 */
public class DatabaseService extends IntentService {
    public static final String API_RESPONSE_OBJECT = "api_response_object";

    private IntentServiceComponent mComponent;

    @Inject
    RxBus sBus;

    public DatabaseService() {
        super(DatabaseService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mComponent = DaggerIntentServiceComponent.builder()
                .applicationComponent(((App) getApplication()).getComponent())
                .build();
        mComponent.inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra(API_RESPONSE_OBJECT)) {
            processParcelableApiResponse((ParcelableApiResponse)
                    intent.getParcelableExtra(API_RESPONSE_OBJECT));
        }
    }

    private void processParcelableApiResponse(ParcelableApiResponse response) {
        int count = 0;

        List<PersistableParent> persistableParents = response.getPersistableParents();
        if (!persistableParents.isEmpty()) {
            ContentResolver resolver = getContentResolver();

            UpsertDirector upsertDirector = new UpsertDirector(persistableParents);
            while (upsertDirector.hasNext()) {
                count += resolver.bulkInsert(
                        upsertDirector.getCurrentUri(),
                        upsertDirector.next()
                );
            }

            List<ContentProviderOperation> deletes = new ArrayList<>();

            // build list of upsert and delete operations
            for (PersistableParent persistableParent : persistableParents) {
                deletes.addAll(persistableParent.getDeleteOperations());
            }

            if (!deletes.isEmpty()) {
                DeleteDirector deleteDirector = new DeleteDirector(deletes);
                while (deleteDirector.hasNext()) {
                    try {
                        resolver.applyBatch(BaseContract.AUTHORITY, deleteDirector.next());
                    } catch (RemoteException e) {
                        // TODO should probably log this...
                    } catch (OperationApplicationException e) {
                        // TODO should probably log this...
                    }
                }
            }
        }

        sBus.send(new DatabaseServiceEvent(count));
    }
}
