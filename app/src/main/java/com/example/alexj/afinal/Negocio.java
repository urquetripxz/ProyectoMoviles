package com.example.alexj.afinal;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.UUID;
import com.example.alexj.afinal.NegocioContract.NegocioEntry;

public class Negocio {
    private String id;
    private String nombre;
    private String categoria;
    private String numero;
    private String bio;
    private String avatarUri;

    public Negocio (String nombre,String categoria,String numero, String bio,String avatarUri){
        this.id = UUID.randomUUID().toString();
        this.nombre=nombre;
        this.categoria=categoria;
        this.numero=numero;
        this.bio=bio;
        this.avatarUri= avatarUri;
    }

    public Negocio(Cursor cursor) {
        id = cursor.getString(cursor.getColumnIndex(NegocioEntry.ID));
        nombre = cursor.getString(cursor.getColumnIndex(NegocioEntry.NOMBRE));
        categoria = cursor.getString(cursor.getColumnIndex(NegocioEntry.CATEGORIA));
        numero = cursor.getString(cursor.getColumnIndex(NegocioEntry.NUMERO));
        bio = cursor.getString(cursor.getColumnIndex(NegocioEntry.BIO));
        avatarUri = cursor.getString(cursor.getColumnIndex(NegocioEntry.AVATAR_URI));


    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(NegocioEntry.ID, id);
        values.put(NegocioEntry.NOMBRE, nombre);
        values.put(NegocioEntry.CATEGORIA, categoria);
        values.put(NegocioEntry.NUMERO, numero);
        values.put(NegocioEntry.BIO, bio);
        values.put(NegocioEntry.AVATAR_URI, avatarUri);
        return values;
    }



public String getId(){
    return id;
}
public String   getNombre(){
    return nombre;
}
    public String getCategoria(){
        return categoria;
    }

    public String getNumero(){
        return numero;

    }

    public String getBio(){
        return bio;
    }

    public String getAvatarUri(){
        return avatarUri;
    }




}

