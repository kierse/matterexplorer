package com.pissiphany.matterexplorer.db.event;

/**
 * Created by kierse on 15-10-03.
 */
public class DatabaseServiceEvent {
    private int mUpsertCount;

    public DatabaseServiceEvent(int upsertCount) {
        this.mUpsertCount = upsertCount;
    }

    public int getUpsertCount() {
        return mUpsertCount;
    }
}
