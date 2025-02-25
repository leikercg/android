package com.example.mibdbasico;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BDHelper extends SQLiteOpenHelper {

    Context context;
    String sqlCreate = "CREATE TABLE Usuarios (codigo INTEGER, nombre TEXT)"; //Variable global

    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        db.execSQL(sqlCreate);
        Toast.makeText(context, "Estoy onUpdate", Toast.LENGTH_SHORT).show();

    }


}
