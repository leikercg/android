package com.example.proyectopsp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static  String DATABASE_NAME = "ciudades.db";
    static  int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) { // crea la tabla
        String CREATE_TABLE = "CREATE TABLE ciudades (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT UNIQUE, " +
                "latitud REAL, " +
                "longitud REAL)";
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { // borra la tabla si cambia la version
        db.execSQL("DROP TABLE IF EXISTS ciudades");
        onCreate(db);
    }
}
