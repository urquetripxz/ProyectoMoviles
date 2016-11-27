package com.example.alexj.afinal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.alexj.afinal.R;

public class NegociosActivity extends AppCompatActivity {
    public static final String EXTRA_NEGOCIO_ID = "extra_negocio_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negocios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NegociosActivityFragment fragment = (NegociosActivityFragment) getSupportFragmentManager().findFragmentById(R.id.negocios_container);
        if (fragment == null) {

            fragment = NegociosActivityFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.negocios_container, fragment)
                    .commit();
        }

    }

}
