package com.produ;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.consumer.activity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutActivity extends AppCompatActivity {


    private Toolbar toolbar;
     static String        serverIP  = "0.0.0.0";
    View view;
    public AboutActivity() {

        // Required empty public constructor
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about);

        toolbar = (Toolbar)findViewById(com.consumer.activity.R.id.about_toolbar);

        setupToolbar();
    }
    public void submitParameter(View v) {
        serverIP = ((TextView) view.findViewById(com.consumer.activity.R.id.paramIP)).getText().toString();
        Toast.makeText(this, "Adresse du serveur modifiée", Toast.LENGTH_SHORT).show();


    }


    private void setupToolbar(){
        toolbar.setTitle(getString(com.consumer.activity.R.string.about_fragment_title));
        setSupportActionBar(toolbar);
    }


}
