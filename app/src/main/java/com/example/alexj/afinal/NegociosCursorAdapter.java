package com.example.alexj.afinal;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.alexj.afinal.R;

/**
 * Created by alexj on 23/11/2016.
 */

public class NegociosCursorAdapter extends CursorAdapter {

    public NegociosCursorAdapter(Context context, Cursor c) {
        super(context,c,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item_negocio, viewGroup, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
