package com.produ;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.consumer.activity.R;
import com.consumer.activity.Resultat;

import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerAdapter;
import db.ProductDb;
import models.CardItemModel;
import models.RecyclerItemClickListener;
import server.model.Product;

public class ScrollingActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener{
    private ProductDb productDb;
    private List<Product> products;
    private List<CardItemModel> cardItems = new ArrayList<>(30);
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        productDb=new ProductDb(this);
        recyclerView = (RecyclerView)findViewById(com.consumer.activity.R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ScrollingActivity.this, this));
        try {
            setupRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ScrollingActivity.this,ProducerActivity.class);
                startActivity(i);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
Intent i=new Intent(ScrollingActivity.this,AboutActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    private void initializeCardItemList() throws Exception {
        CardItemModel cardItemModel;
        products=new ArrayList<Product>();
        products=productDb.getAllProduct();

        for(int i=0;i<products.size();i++){
            Log.e(""+products.get(i).getId(),products.get(i).getCategory());
            cardItemModel = new CardItemModel(products.get(i).getName(),products.get(i).getCategory());
            cardItems.add(cardItemModel);
        }
    }
    private void setupRecyclerView() throws Exception {
        recyclerView.setLayoutManager(new LinearLayoutManager(ScrollingActivity.this));
        recyclerView.setHasFixedSize(true);
        initializeCardItemList();
        recyclerAdapter=new RecyclerAdapter(cardItems);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onItemClick(View childView, int position) {
        Intent i=new Intent(ScrollingActivity.this,Resultat.class);
        Product p=products.get(position);
        i.putExtra("id", p.getId());
        startActivity(i);
    }

    @Override
    public void onItemLongPress(View childView, int position) {
        Product p=products.get(position);
        int id=p.getId();
        productDb.deleteEntry(id);
        recyclerAdapter.removeAt(position);

    }
}
