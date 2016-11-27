package com.example.alexj.afinal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.Toast;
import com.example.alexj.afinal.R;
import com.example.alexj.afinal.Negocio;
import com.example.alexj.afinal.NegociosDBHelper;




/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditNegocioActivityFragment extends Fragment {
    private static final String ARG_NEGOCIO_ID ="arg_negocio_id";
    private String mNegocioId;
    private NegociosDBHelper mNegociosDbHelper;
    private FloatingActionButton mSaveButton;
    private TextInputEditText mNombreField;
    private TextInputEditText mNumeroField;
    private TextInputEditText mCategoriaField;
    private TextInputEditText mBioField;
    private TextInputLayout mNombreLabel;
    private TextInputLayout mNumeroLabel;
    private TextInputLayout mCategoriaLabel;
    private TextInputLayout mBioLabel;


    public AddEditNegocioActivityFragment() {
    }

    public static AddEditNegocioActivityFragment newInstance(String negocioId) {
        AddEditNegocioActivityFragment fragment = new AddEditNegocioActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NEGOCIO_ID, negocioId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNegocioId = getArguments().getString(ARG_NEGOCIO_ID);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_edit_negocio, container, false);

        // Referencias UI
        mSaveButton = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mNombreField = (TextInputEditText) root.findViewById(R.id.et_nombre);
        mNumeroField = (TextInputEditText) root.findViewById(R.id.et_numero);
        mCategoriaField = (TextInputEditText) root.findViewById(R.id.et_categoria);
        mBioField = (TextInputEditText) root.findViewById(R.id.et_bio);
        mNombreLabel = (TextInputLayout) root.findViewById(R.id.til_nombre);
        mNumeroLabel = (TextInputLayout) root.findViewById(R.id.til_numero);
        mCategoriaLabel = (TextInputLayout) root.findViewById(R.id.til_categoria);
        mBioLabel = (TextInputLayout) root.findViewById(R.id.til_bio);

        // Eventos
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditLawyer();
            }
        });

        mNegociosDbHelper = new NegociosDBHelper(getActivity());

        // Carga de datos
        if (mNegocioId != null) {
            loadNegocio();
        }

        return root;
    }

    private void loadNegocio() {
        new GetNegocioByIdTask().execute();
    }

    private void addEditLawyer() {
        boolean error = false;

        String nombre = mNombreField.getText().toString();
        String numero = mNumeroField.getText().toString();
        String categoria = mCategoriaField.getText().toString();
        String bio = mBioField.getText().toString();

        if (TextUtils.isEmpty(nombre)) {
            mNombreLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(numero)) {
            mNumeroLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (TextUtils.isEmpty(categoria)) {
            mCategoriaLabel.setError(getString(R.string.field_error));
            error = true;
        }


        if (TextUtils.isEmpty(bio)) {
            mBioLabel.setError(getString(R.string.field_error));
            error = true;
        }

        if (error) {
            return;
        }

        Negocio negocio = new Negocio(nombre,categoria,numero,bio,"");



        new AddEditNegocioTask().execute(negocio);

    }

    private void showNegociosScreen(Boolean requery) {
        if (!requery) {
            showAddEditError();
            getActivity().setResult(Activity.RESULT_CANCELED);
        } else {
            getActivity().setResult(Activity.RESULT_OK);
        }

        getActivity().finish();
    }

    private void showAddEditError() {
        Toast.makeText(getActivity(),
                "Error al agregar nueva informacion", Toast.LENGTH_SHORT).show();
    }

    private void showLawyer(Negocio negocio) {
        mNombreField.setText(negocio.getNombre());
        mNumeroField.setText(negocio.getNumero());
        mCategoriaField.setText(negocio.getCategoria());
        mBioField.setText(negocio.getBio());
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al editar negocio", Toast.LENGTH_SHORT).show();
    }

    private class GetNegocioByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mNegociosDbHelper.getNegocioById(mNegocioId);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showLawyer(new Negocio(cursor));
            } else {
                showLoadError();
                getActivity().setResult(Activity.RESULT_CANCELED);
                getActivity().finish();
            }
        }

    }

    private class AddEditNegocioTask extends AsyncTask<Negocio, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Negocio... negocios) {
            if (mNegocioId != null) {
                return mNegociosDbHelper.updateNegocio(negocios[0], mNegocioId) > 0;

            } else {
                return mNegociosDbHelper.saveNegocio(negocios[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showNegociosScreen(result);
        }

    }

}

