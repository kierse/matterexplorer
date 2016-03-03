package com.pissiphany.matterexplorer.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.pissiphany.matterexplorer.R;
import com.pissiphany.matterexplorer.view.ui.matter.MatterActivity;
import com.pissiphany.matterexplorer.view.ui.rx.RxMainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.matters)
    Button mMattersButton;

    @Bind(R.id.rx_download_file)
    Button mRxDownloadFileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.matters)
    void onClickMattersButton() {
        startActivity(new Intent(this, MatterActivity.class));
    }

    @OnClick(R.id.rx_download_file)
    void onClickRxDownloadFile() {
        startActivity(new Intent(this, RxMainActivity.class));
    }
}
