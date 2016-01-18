package com.consumer.activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;



/**
 * A simple {@link Fragment} subclass.
 */
public class ConsumerFragment extends Fragment {
    private static int RESULT_LOAD_IMAGE = 1;

    View view;
    private MainActivity mainActivity;
    private Toolbar toolbar;

    private TextInputLayout inputLayoutName;
    private EditText inputName;
    public ConsumerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity)activity;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view =  inflater.inflate(com.consumer.activity.R.layout.fragment_consumer, container, false);


        toolbar = (Toolbar)view.findViewById(com.consumer.activity.R.id.about_toolbar);

        setupToolbar();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle(getString(com.consumer.activity.R.string.about_fragment_title));
        mainActivity.setSupportActionBar(toolbar);
    }


}
