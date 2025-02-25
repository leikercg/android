package com.example.sorpresa;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sorpresa.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BDHelper extends SQLiteOpenHelper {

    private Context context; // Guardamos el

    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context; // Asignamos el contexto

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Toast.makeText(context, "oncrete", Toast.LENGTH_SHORT).show();

        String linea;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.bdestacionesesqui);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            while ((linea = br.readLine()) != null) { // Primero se lee la línea
                Log.d("linea", linea);
                db.execSQL(linea); // Se ejecuta la línea
            }

            br.close();
            is.close();
        } catch (Exception e) {
            Log.e("Error", "Error al leer el archivo SQL: " + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        Toast.makeText(context, "UPGRADe", Toast.LENGTH_SHORT).show();
        db.execSQL("DROP TABLE IF EXISTS esqui");
        onCreate(db);


    }
}
