package com.produ;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.consumer.activity.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import config.Configuration;
import db.ProductDb;
import server.model.Producer;
import server.model.Product;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProducerActivity extends AppCompatActivity {;

    private Toolbar toolbar;


    private  ProductDb productDb;
    private static int RESULT_LOAD_IMAGE = 1;
    static byte[] b;
    boolean video=false;
    boolean ima=false;
    public ProducerActivity() {
        // Required empty public constructor
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_produce);
        productDb=new ProductDb(this);


        toolbar = (Toolbar)findViewById(R.id.about_toolbar);

        setupToolbar();


    }

    public void submitProduct(View v) throws Exception {
        String sName = ((TextView) findViewById(R.id.pName)).getText().toString();
        String sCat = ((TextView) findViewById(R.id.pCategory)).getText().toString();
        String sPrice = ((TextView) findViewById(R.id.pPrice)).getText().toString();
        String sDesc = ((TextView) findViewById(R.id.pDes)).getText().toString();
        String sAdd = ((TextView) findViewById(R.id.pAddress)).getText().toString();
        String sCity = ((TextView) findViewById(R.id.pCity)).getText().toString();
        byte[] image=b;
        double price = Double.parseDouble(sPrice);
        Product product = new Product(sName,sCat,price,sDesc,image,image,Double.parseDouble(sAdd), Double.parseDouble(sCity));
        productDb.insert(product);
        List<Product> products=productDb.getAllProduct();
        Producer<Product> producer = new Producer<Product>(AboutActivity.serverIP, Configuration.BROKER_RECEIVE_PORT, product);
        new Thread(producer).start();

        Toast.makeText(getApplicationContext(), "Produit en cours d'envoi", Toast.LENGTH_SHORT).show();
        Intent i=new Intent(ProducerActivity.this,ScrollingActivity.class);
        startActivity(i);
    }


    public  void loadImage(View v)
    {
        ima=true;
        video=false;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), 1);

    }

    public  void loadVideo(View v)
    {

        video=true;
        ima=false;
        Log.e("image",""+ima);
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select video"), 1);

    }

    public  void loadMap(View v)
    {

        Snackbar.make(findViewById(com.consumer.activity.R.id.buttonMap),
                "Erreur", Snackbar.LENGTH_LONG)
                .show();

    }
    private void setupToolbar(){
        toolbar.setTitle(getString(R.string.about_fragment_title));
        setSupportActionBar(toolbar);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File imagefile = new File(picturePath);
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(imagefile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
if(ima==true) {
    Bitmap bm = BitmapFactory.decodeStream(fis);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    b = baos.toByteArray();
    ImageView imageView = (ImageView) findViewById(R.id.imgView);
    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
}
            if(video==true){
                VideoView videoView = (VideoView) findViewById(R.id.vidView);
                videoView.setVideoURI(selectedImage);
                videoView.requestFocus();
                videoView.start();}
        }
    }

}
