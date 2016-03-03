package com.pissiphany.matterexplorer.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.pissiphany.matterexplorer.R;
import com.pissiphany.matterexplorer.model.provider.contract.MatterContract;

/**
 * Created by kierse on 15-10-04.
 */
public class MatterListCursorAdapter extends CursorAdapter {

    public MatterListCursorAdapter(Context context) {
        super(context, null, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView displayNumber = (TextView) view.findViewById(R.id.primary_text);
        TextView description = (TextView) view.findViewById(R.id.secondary_text);
        TextView status = (TextView) view.findViewById(R.id.tertiary_text);

        displayNumber.setText(getCursorString(cursor, MatterContract.Columns.DISPLAY_NUMBER));
        description.setText(getCursorString(cursor, MatterContract.Columns.DESCRIPTION));
        status.setText(getCursorString(cursor, MatterContract.Columns.STATUS));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    private String getCursorString(Cursor cursor, String column) {
        return cursor.getString(cursor.getColumnIndex(column));
    }
}
