package com.consumer.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import adapters.RecyclerAdapter;
import adapters.RecyclerItemClickListener;
import db.ProductDb;
import models.CardItemModel;
import server.model.Product;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollapsingToolbarFragment extends Fragment implements RecyclerItemClickListener.OnItemClickListener {


    private ProductDb productDb;
    private List<Product> products;

    private RecyclerAdapter recyclerAdapter;
    private List<CardItemModel> cardItems = new ArrayList<>(30);
    private MainActivity appCompatActivity;
    private Toolbar toolbar;
    private RecyclerView recyclerView;


    public CollapsingToolbarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        appCompatActivity = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.consumer.activity.R.layout.fragment_collapsing_toolbar, container, false);

        toolbar = (Toolbar)view.findViewById(com.consumer.activity.R.id.toolbar);

        setupToolbar();
        productDb=new ProductDb(getActivity());

        ((CollapsingToolbarLayout)view.findViewById(com.consumer.activity.R.id.collapsing_toolbar)).setTitle(
                getString(com.consumer.activity.R.string.collapsing_toolbar_fragment_title));

        recyclerView = (RecyclerView)view.findViewById(com.consumer.activity.R.id.recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), this));

        try {
            setupRecyclerView();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appCompatActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        initializeCardItemList();
        recyclerAdapter=new RecyclerAdapter(cardItems);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onItemClick(View childView, int position) {
        Product p=products.get(position);
        Intent i=new Intent(getActivity(),ResultActivityI.class);
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
