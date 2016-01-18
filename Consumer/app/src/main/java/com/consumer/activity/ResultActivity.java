package com.consumer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import config.Configuration;
import db.ProductDb;
import server.callback.OnConsume;
import server.model.Consumer;
import server.model.Product;

public class ResultActivity extends AppCompatActivity {
Product product=new Product();
    private ProductDb productDb;

    TextView textView;
    TextView catT;
    TextView prixT;
    TextView desT;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        productDb=new ProductDb(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         image= (ImageView)findViewById(R.id.image);

        textView=(TextView)findViewById(R.id.name);
        catT = (TextView) findViewById(R.id.cat);
        prixT = (TextView) findViewById(R.id.prix);
        desT = (TextView) findViewById(R.id.des);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_keyboard_backspace_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ResultActivity.this,MainActivity.class);
            startActivity(i);}
        });
       product=ScrollingActivity.productResult;
        Consumer<Product> consumer = new Consumer<Product>(Configuration.CLIENT_RECEIVE_PORT);
        consumer.setOnConsume(new OnConsume<Product>() {
            @Override
            public void onConsume(Product productt, String sourceIP) {
                textView.setText(productt.getName());
                catT.setText(product.getCategory());
                prixT.setText("" + product.getPrice());
                desT.setText(product.getDescription());
                byte[] bytes = product.getThumbnail();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                image.setImageBitmap(bitmap);

            }
        });

        new Thread(consumer).start();

        textView.setText(product.getName()+" "+product.getDescription() );
        catT.setText(product.getCategory());
        prixT.setText("" + product.getPrice());
        desT.setText(product.getDescription());
        byte[] bytes=product.getThumbnail();

        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        image.setImageBitmap(bitmap);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    productDb.insert(product);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                // ProjectsActivity is my 'home' activity
                super. onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
