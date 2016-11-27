package com.example.alexj.afinal;

/**
 * Created by alexj on 21/11/2016.
 */
import android.provider.BaseColumns;

public class NegocioContract {
    public static abstract class NegocioEntry implements BaseColumns {
        public static final String TABLE_NAME="negocio";
        public static final String ID="id";
        public static final String NOMBRE="nombre";
        public static final String CATEGORIA="categoria";
        public static final String NUMERO="numero";
        public static final String AVATAR_URI = "avatarUri";
        public static final String BIO="bio";
    }
}
