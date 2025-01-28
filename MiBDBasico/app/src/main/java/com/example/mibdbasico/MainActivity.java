package com.example.mibdbasico;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etCodigo, etNombre;
    BDHelper usdbh;
    SQLiteDatabase dbw,dbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etCodigo = findViewById(R.id.txtReg);
        etNombre = findViewById(R.id.txtVal);


        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new BDHelper(this, "DBUsuarios", null, 4);
        //dbw = usdbh.getWritableDatabase(); // No ayadimos datos solo para pruebas

        //Si hemos abierto correctamente la base de datos
        if (dbw != null) {
            //Insertamos 5 usuarios de ejemplo
            for (int i = 1; i <= 5; i++) {
                //Generamos los datos
                int codigo = i;
                String nombre = "Usuario" + i;

                //Insertamos los datos en la tabla Usuarios
                dbw.execSQL("INSERT INTO Usuarios (codigo, nombre) " +
                        "VALUES (" + codigo + ", '" + nombre + "')");
            }

            //Cerramos la base de datos
            //dbw.close();
        }

    }

    public void clickInsertar(View v) {

        String codigo = etCodigo.getText().toString(); // Recogemos el valor del código
        String nombre = etNombre.getText().toString(); // Recogemos el valor del nombre

        String sql = "INSERT INTO Usuarios (codigo, nombre) VALUES ('" + codigo + "', '" + nombre + "')";

        dbw=usdbh.getWritableDatabase();
        dbw.execSQL(sql);

      /*  ContentValues cv = new ContentValues();
        cv.put("CODIGO", codigo);
        cv.put("NOMBRE", nombre);
        dbw.insert("USUARIOS", null, cv); // metodo android*/
    }
}