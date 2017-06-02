package com.example.thomas.center;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thomas.center.model.Shop;
import com.example.thomas.center.util.CalendarManager;
import com.example.thomas.center.util.DownloadImageTask;

/**
 * Created by ttetu on 29/05/2017.
 */

public class EventActivity extends AppCompatActivity {

    private CalendarManager calendarManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendarManager = new CalendarManager(getBaseContext());
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getIntent() != null) {
            Intent intent = getIntent();
            setTitle(intent.getStringExtra("title"));
            final Shop shop = intent.getParcelableExtra("shop");

            TextView description = (TextView)findViewById(R.id.descriptionText);
            description.setText(shop.getDescription());

            ImageView imageView = (ImageView)findViewById(R.id.eventImage);
            DownloadImageTask imageTask = new DownloadImageTask(imageView,this);
            imageTask.execute(shop.getImgPath());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
    }
}
