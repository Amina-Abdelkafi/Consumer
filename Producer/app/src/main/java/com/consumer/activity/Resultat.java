package com.consumer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.produ.ScrollingActivity;

import db.ProductDb;
import server.model.Product;

public class Resultat extends AppCompatActivity {
    private ProductDb productDb;
    private Product product;
    TextView catT;
    TextView prixT;
    TextView desT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView image= (ImageView)findViewById(R.id.image);
        TextView tName=(TextView)findViewById(R.id.name);
        catT = (TextView) findViewById(R.id.cat);
        prixT = (TextView) findViewById(R.id.prix);
        desT = (TextView) findViewById(R.id.des);
Intent i=getIntent();
        productDb=new ProductDb(this);
        toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.ic_keyboard_backspace_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Resultat.this,ScrollingActivity.class);
                startActivity(i);}
        });
        Bundle extras = i.getExtras();
    if(extras != null) {
        int id = extras.getInt("id");
        Log.e("id", ""+id);

        product=new Product();
        try {
            product=productDb.getProductId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tName.setText(product.getName());

        catT.setText(product.getCategory());
        prixT.setText(""+product.getPrice());
        desT.setText(product.getDescription());
        byte[] bytes=product.getThumbnail();
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        image.setImageBitmap(bitmap);





    }
        setSupportActionBar(toolbar);

    }

}
