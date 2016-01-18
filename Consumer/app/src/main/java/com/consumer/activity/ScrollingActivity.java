package com.consumer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerAdapter;
import adapters.RecyclerItemClickListener;
import config.Configuration;
import models.CardItemModel;
import server.model.Producer;
import server.model.Product;
import server.model.ProductRequest;

public class ScrollingActivity extends AppCompatActivity implements RecyclerItemClickListener.OnItemClickListener{
    private List<Product> products;
    private List<CardItemModel> cardItems = new ArrayList<>(30);
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    static Product productResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        recyclerView = (RecyclerView)findViewById(com.consumer.activity.R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ScrollingActivity.this, this));
        try {
            setupRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private void initializeCardItemList() throws Exception {
        CardItemModel cardItemModel;
        products=new ArrayList<Product>();
        products=MainActivity.products;

        for(int i=0;i<products.size();i++){
            cardItemModel = new CardItemModel(products.get(i).getName(),""+products.get(i).getPrice());
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
        Product p=products.get(position);
        ProductRequest productReques=new ProductRequest();
        productReques.setId(p.getId());
        Producer<ProductRequest> producer = new Producer<ProductRequest>(MainActivity.serverIP, Configuration.BROKER_RECEIVE_PORT,
                productReques);
        new Thread(producer).start();
        Intent i=new Intent(ScrollingActivity.this,ResultActivity.class);
productResult=p;
        startActivity(i);
    }

    @Override
    public void onItemLongPress(View childView, int position) {

    }


}
