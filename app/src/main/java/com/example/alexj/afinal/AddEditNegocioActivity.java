package com.example.alexj.afinal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.alexj.afinal.NegociosActivity;
import com.example.alexj.afinal.R;


public class AddEditNegocioActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_NEGOCIO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_negocio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String negocioId = getIntent().getStringExtra(NegociosActivity.EXTRA_NEGOCIO_ID);

        setTitle(negocioId == null ? "Añadir Negocio" : "Editar Negocio");

        AddEditNegocioActivityFragment addEditNegocioFragment = (AddEditNegocioActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.add_edit_negocio_container);
        if (addEditNegocioFragment == null) {
            addEditNegocioFragment = addEditNegocioFragment.newInstance(negocioId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.add_edit_negocio_container, addEditNegocioFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
