package com.pissiphany.matterexplorer.service;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.pissiphany.matterexplorer.db.DeleteDirector;
import com.pissiphany.matterexplorer.db.UpsertDirector;
import com.pissiphany.matterexplorer.model.PersistableParent;
import com.pissiphany.matterexplorer.provider.contract.BaseContract;
import com.pissiphany.matterexplorer.network.api.ParcelableApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kierse on 15-09-18.
 */
public class DatabaseService extends IntentService {
    public static final String API_RESPONSE_OBJECT = "api_response_object";

    public DatabaseService() {
        super(DatabaseService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra(API_RESPONSE_OBJECT)) {
            ParcelableApiResponse response = intent.getParcelableExtra(API_RESPONSE_OBJECT);

            List<PersistableParent> persistableParents = response.getPersistableParents();
            if (!persistableParents.isEmpty()) {
                ContentResolver resolver = getContentResolver();

                UpsertDirector upsertDirector = new UpsertDirector(persistableParents);
                while (upsertDirector.hasNext()) {
                    resolver.bulkInsert(
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
        }
    }
}
