package com.example.thomas.center;

/**
 * Created by thomas on 18/04/17.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.GridView;

import com.example.thomas.center.adapter.GridProductAdapter;
import com.example.thomas.center.model.Product;
import com.example.thomas.center.model.Shop;

import java.util.List;

public class ProductsActivity extends AppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getIntent() != null) {
            Intent intent = getIntent();
            setTitle(intent.getStringExtra("title"));
            Shop shop = intent.getParcelableExtra("shop");
            GridView gridView = (GridView) findViewById(R.id.gridView);
            gridView.setAdapter(new GridProductAdapter(this,shop.getProductList()));

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
