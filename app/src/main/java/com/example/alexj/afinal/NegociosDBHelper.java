package com.example.alexj.afinal;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.alexj.afinal.NegocioContract.NegocioEntry;

public class NegociosDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME="Negocios.db";

    public NegociosDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NegocioEntry.TABLE_NAME + " ("
                + NegocioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NegocioEntry.ID + " TEXT NOT NULL,"
                + NegocioEntry.NOMBRE + " TEXT NOT NULL,"
                + NegocioEntry.CATEGORIA + " TEXT NOT NULL,"
                + NegocioEntry.NUMERO + " TEXT NOT NULL,"
                + NegocioEntry.BIO + " TEXT NOT NULL,"
                + NegocioEntry.AVATAR_URI + " TEXT,"
                + "UNIQUE (" + NegocioEntry.ID + "))");



        // Insertar datos ficticios para prueba inicial
        mockData(db);

    }



    private void mockData(SQLiteDatabase sqLiteDatabase) {
        mockNegocio(sqLiteDatabase, new Negocio("Paquito.inc", "Bienes Raices",
                "22222222", "Agencia de bienes raices de managua",
                "carlos_perez.jpg"));
    }


    public long mockNegocio(SQLiteDatabase db, Negocio lawyer) {
        return db.insert(
                NegocioEntry.TABLE_NAME,
                null,
                lawyer.toContentValues());
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long saveNegocio(Negocio negocio) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                NegocioEntry.TABLE_NAME,
                null,
                negocio.toContentValues());

    }


    public Cursor getAllNegocios() {
        return getReadableDatabase()
                .query(
                        NegocioEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }
    public Cursor getNegocioById(String negocioId) {
        Cursor c = getReadableDatabase().query(
                NegocioEntry.TABLE_NAME,
                null,
                NegocioEntry.ID + " LIKE ?",
                new String[]{negocioId},
                null,
                null,
                null);
        return c;
    }

    public int deleteNegocio(String negocioId) {
        return getWritableDatabase().delete(
                NegocioEntry.TABLE_NAME,
                NegocioEntry.ID + " LIKE ?",
                new String[]{negocioId});
    }
    public int updateNegocio(Negocio negocio, String negocioId) {
        return getWritableDatabase().update(
                NegocioEntry.TABLE_NAME,
                negocio.toContentValues(),
                NegocioEntry.ID + " LIKE ?",
                new String[]{negocioId}
        );
    }




}
