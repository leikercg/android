package com.example.sorpresa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.IOException;

public class BDHelper extends SQLiteOpenHelper {

    String sqlCreate = ""; // Variable global donde se almacenará el SQL

    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        leerSql(context); // Llamamos al método para leer el archivo SQL al inicializar la base de datos
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Asegúrate de que sqlCreate contiene el script SQL antes de ejecutarlo

            // Ejecutar la sentencia SQL leída
            db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        // Eliminar la tabla si existe y crearla nuevamente
        db.execSQL("DROP TABLE IF EXISTS esqui"); // Corregido: Nombre de la tabla 'esqui'
        onCreate(db);
    }

    // Método para leer el archivo SQL desde raw
    public void leerSql(Context context) {
        sqlCreate = ""; // Inicializamos la variable que almacenará el script SQL

        try {
            // Abrir el archivo desde raw (bdestacionesesqui.sql)
            InputStream inputStream = context.getResources().openRawResource(R.raw.bdestacionesesqui); // Asegúrate de tener este archivo en res/raw
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line); // Agregar cada línea al StringBuilder
                sqlCreate = stringBuilder.toString(); // Asignar el contenido al SQL que ejecutará la base de datos
            }
            reader.close();

            sqlCreate = stringBuilder.toString(); // Asignar el contenido al SQL que ejecutará la base de datos

            Log.i("Ficheros", "Fichero leído!");
            Log.i("Ficheros", "Texto: " + sqlCreate);
        } catch (IOException e) {
            Log.e("Ficheros", "Error al leer fichero desde raw", e);
        }
    }
}

