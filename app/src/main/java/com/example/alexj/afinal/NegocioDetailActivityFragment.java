package com.example.alexj.afinal;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.alexj.afinal.R;
import com.example.alexj.afinal.AddEditNegocioActivity;
import com.example.alexj.afinal.Negocio;
import com.example.alexj.afinal.NegociosDBHelper;
import com.example.alexj.afinal.NegociosActivity;
import com.example.alexj.afinal.NegociosActivityFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class NegocioDetailActivityFragment extends Fragment {
    private static final String ARG_NEGOCIO_ID ="negocioId";
    private String mNegocioId;
    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView mNumero;
    private TextView mCategoria;
    private TextView mBio;
    private NegociosDBHelper mNegociosDbHelper;

    public NegocioDetailActivityFragment() {
    }

    public static NegocioDetailActivityFragment newInstance(String negocioId) {
        NegocioDetailActivityFragment fragment = new NegocioDetailActivityFragment();
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

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_negocio_detail, container, false);
        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        mNumero = (TextView) root.findViewById(R.id.tv_numero);
        mCategoria = (TextView) root.findViewById(R.id.tv_categoria);
        mBio = (TextView) root.findViewById(R.id.tv_bio);

        mNegociosDbHelper = new NegociosDBHelper(getActivity());

        loadNegocio();

        return root;
    }

    private void loadNegocio() {
        new GetNegocioByIdTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                new DeleteNegocioTask().execute();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NegociosActivityFragment.REQUEST_UPDATE_DELETE_NEGOCIO) {
            if (resultCode == Activity.RESULT_OK) {
                getActivity().setResult(Activity.RESULT_OK);
                getActivity().finish();
            }
        }
    }

    private void showLawyer(Negocio negocio) {
        mCollapsingView.setTitle(negocio.getNombre());
        Glide.with(this)
                .load(Uri.parse("file:///android_asset/" + negocio.getAvatarUri()))
                .centerCrop()
                .into(mAvatar);
        mNumero.setText(negocio.getNumero());
        mCategoria.setText(negocio.getCategoria());
        mBio.setText(negocio.getBio());
    }

    private void showEditScreen() {
        Intent intent = new Intent(getActivity(), AddEditNegocioActivity.class);
        intent.putExtra(NegociosActivity.EXTRA_NEGOCIO_ID, mNegocioId);
        startActivityForResult(intent, NegociosActivityFragment.REQUEST_UPDATE_DELETE_NEGOCIO);
    }

    private void showNegociosScreen(boolean requery) {
        if (!requery) {
            showDeleteError();
        }
        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
    }

    private void showLoadError() {
        Toast.makeText(getActivity(),
                "Error al cargar informacion", Toast.LENGTH_SHORT).show();
    }

    private void showDeleteError() {
        Toast.makeText(getActivity(),
                "Error al eliminar negocio", Toast.LENGTH_SHORT).show();
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
            }
        }

    }

    private class DeleteNegocioTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return mNegociosDbHelper.deleteNegocio(mNegocioId);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showNegociosScreen(integer > 0);
        }

    }

}
