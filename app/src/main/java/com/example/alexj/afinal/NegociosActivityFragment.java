package com.example.alexj.afinal;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.alexj.afinal.R;
import com.example.alexj.afinal.NegociosDBHelper;
import com.example.alexj.afinal.NegocioContract.NegocioEntry;

/**
 * A placeholder fragment containing a simple view.
 */
public class NegociosActivityFragment extends Fragment {

    public static final int REQUEST_UPDATE_DELETE_NEGOCIO =2;
    private NegociosDBHelper mNegociosDbHelper;
    private ListView mNegociosList;
    private NegociosCursorAdapter mNegociosAdapter;
    private FloatingActionButton mAddButton;
    public NegociosActivityFragment() {
    }

    public static NegociosActivityFragment newInstance(){
        return new NegociosActivityFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_negocios,container,false);


        mNegociosList=(ListView)root.findViewById(R.id.negocios_list);
        mNegociosAdapter = new NegociosCursorAdapter(getActivity(),null);
        mAddButton=(FloatingActionButton)getActivity().findViewById(R.id.fab);

        mNegociosList.setAdapter(mNegociosAdapter);
        mNegociosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor currentItem = (Cursor) mNegociosAdapter.getItem(i);
                String currentNegocioId = currentItem.getString(
                        currentItem.getColumnIndex(NegocioEntry.ID));

                showDetailScreen(currentNegocioId);
            }
        });
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddScreen();
            }
        });

        getActivity().deleteDatabase(NegociosDBHelper.DATABASE_NAME);

        // Instancia de helper
        mNegociosDbHelper = new NegociosDBHelper(getActivity());

        // Carga de datos
        loadNegocios();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case AddEditNegocioActivity.REQUEST_ADD_NEGOCIO:
                    showSuccessfullSavedMessage();
                    loadNegocios();
                    break;
                case REQUEST_UPDATE_DELETE_NEGOCIO:
                    loadNegocios();
                    break;
            }
        }
    }

    private void loadNegocios() {
        new LawyersLoadTask().execute();
    }

    private void showSuccessfullSavedMessage() {
        Toast.makeText(getActivity(),
                "Negocio guardado correctamente", Toast.LENGTH_SHORT).show();
    }

    private void showAddScreen() {
        Intent intent = new Intent(getActivity(), AddEditNegocioActivity.class);
        startActivityForResult(intent, AddEditNegocioActivity.REQUEST_ADD_NEGOCIO);
    }

    private void showDetailScreen(String negocioId) {
        Intent intent = new Intent(getActivity(), NegocioDetailActivity.class);
        intent.putExtra(NegociosActivity.EXTRA_NEGOCIO_ID, negocioId);
        startActivityForResult(intent, REQUEST_UPDATE_DELETE_NEGOCIO);
    }

    private class LawyersLoadTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mNegociosDbHelper.getAllNegocios();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.getCount() > 0) {
                mNegociosAdapter.swapCursor(cursor);
            } else {
                // Mostrar empty state
            }
        }
    }

}
