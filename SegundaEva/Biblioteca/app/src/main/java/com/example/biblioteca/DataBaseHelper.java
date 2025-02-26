package com.example.biblioteca;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    Context context;
    String sqlCreate = "CREATE TABLE libros (_id integer primary key autoincrement, \n" +
            "\t categoria text not null, titulo text not null, autor text not null, idioma text, \n" +
            "\t fecha_lectura_ini long, fecha_lectura_fin long, prestado_a text, valoracion float,  \n" +
            "        formato text, notas text);"; //Variable global

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // Para poder acceder a la base de datos
        db.execSQL(sqlCreate); // Ejecutamos la sentencia.
        Toast.makeText(context, "Estoy onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) { // si la version nueva es mas nueva se ejecuta esto
        db.execSQL("DROP TABLE IF EXISTS libros");
        db.execSQL(sqlCreate);
        Toast.makeText(context, "Estoy onUpdate", Toast.LENGTH_SHORT).show();
    }
}
