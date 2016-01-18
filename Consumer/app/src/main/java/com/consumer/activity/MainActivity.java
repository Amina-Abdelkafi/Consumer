package com.consumer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import config.Configuration;
import gps.GPSTracker;
import server.callback.OnConsume;
import server.model.Consumer;
import server.model.MatchingProductsRequest;
import server.model.Producer;
import server.model.Product;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    private static int RESULT_LOAD_IMAGE = 1;
    static byte[] b;
    static Product productResult;

    private final static String COLLAPSING_TOOLBAR_FRAGMENT_TAG = "collapsing_toolbar";
    private final static String FAB_FRAGMENT_TAG = "fab";
    private final static String ABOUT_FRAGMENT_TAG = "about";
    private final static String SELECTED_TAG = "selected_index";
    private final static int COLLAPSING_TOOLBAR = 0;
    private final static int FAB = 1;
    private final static int ABOUT = 3;
    static String        serverIP  = "0.0.0.0";
    private static int selectedIndex;
    static List<Product> products;
    GPSTracker gps;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private View dialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(com.consumer.activity.R.layout.activity_main);

        navigationView = (NavigationView)findViewById(com.consumer.activity.R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = (DrawerLayout)findViewById(com.consumer.activity.R.id.drawer_layout);

        if(savedInstanceState!=null){
            navigationView.getMenu().getItem(savedInstanceState.getInt(SELECTED_TAG)).setChecked(true);
            return;
        }

        selectedIndex = COLLAPSING_TOOLBAR;

        getSupportFragmentManager().beginTransaction().add(com.consumer.activity.R.id.fragment_container,
                new CollapsingToolbarFragment(),COLLAPSING_TOOLBAR_FRAGMENT_TAG).commit();



        Consumer<List<Product>> consumer = new Consumer<List<Product>>(Configuration.CLIENT_RECEIVE_PORT);
        consumer.setOnConsume(new OnConsume<List<Product>>() {
            @Override
            public void onConsume(List<Product> product, String sourceIP) {
                Intent i = new Intent(MainActivity.this, ScrollingActivity.class);
                products = product;


                i.putExtra("id", products.get(0).getName());
                MainActivity.this.startActivity(i);
            }
        });

        new Thread(consumer).start();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAG, selectedIndex);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.consumer.activity.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch(menuItem.getItemId()){
            case com.consumer.activity.R.id.item_collapsing_toolbar:
                if(!menuItem.isChecked()){
                    selectedIndex = COLLAPSING_TOOLBAR;
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(com.consumer.activity.R.id.fragment_container,
                            new CollapsingToolbarFragment(), COLLAPSING_TOOLBAR_FRAGMENT_TAG).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            case com.consumer.activity.R.id.item_fab:
                if(!menuItem.isChecked()){
                    selectedIndex = FAB;
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(com.consumer.activity.R.id.fragment_container,
                            new ConsumerFragment(),FAB_FRAGMENT_TAG).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;

            case com.consumer.activity.R.id.item_about:
                if(!menuItem.isChecked()){
                    selectedIndex = ABOUT;
                    menuItem.setChecked(true);
                    getSupportFragmentManager().beginTransaction().replace(com.consumer.activity.R.id.fragment_container,
                            new AboutFragment(),ABOUT_FRAGMENT_TAG).commit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        return false;
    }


    //Click listener for Snackbar UNDO action
    @Override
    public void onClick(View view) {
    }

    public void setupNavigationDrawer(Toolbar toolbar){
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                com.consumer.activity.R.string.open_drawer, com.consumer.activity.R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    public void submitProductRequest(View v) {
        String sCat = ((TextView) findViewById(com.consumer.activity.R.id.pCategory)).getText().toString();
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            MatchingProductsRequest matchingProductsRequest = new MatchingProductsRequest(sCat,latitude,longitude);

            Producer<MatchingProductsRequest> producer = new Producer<MatchingProductsRequest>(serverIP, Configuration.BROKER_RECEIVE_PORT,
                    matchingProductsRequest);
            new Thread(producer).start();

            Toast.makeText(getApplicationContext(), "Requête en cours d'envoi", Toast.LENGTH_SHORT).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
    public void submitParameter(View v) {
       serverIP = ((TextView) findViewById(R.id.paramIP)).getText().toString();
        Toast.makeText(getApplicationContext(), "Adresse du serveur modifiée", Toast.LENGTH_SHORT).show();
    }




}
