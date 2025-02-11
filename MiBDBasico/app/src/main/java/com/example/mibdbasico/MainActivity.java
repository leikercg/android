package com.example.mibdbasico;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etCodigo, etNombre;
    BDHelper usdbh;
    SQLiteDatabase dbw, dbr;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etCodigo = findViewById(R.id.txtReg);
        etNombre = findViewById(R.id.txtVal);
        txtResultado = findViewById(R.id.txtResultado);


        //Abrimos la base de datos 'DBUsuarios' en modo escritura
        usdbh = new BDHelper(this, "DBUsuarios", null, 5);
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

        dbw = usdbh.getWritableDatabase();
        dbw.execSQL(sql); // Ejecuta sentencia sql sin más.

      /*  ContentValues cv = new ContentValues(); // función propia de android
        cv.put("CODIGO", codigo);
        cv.put("NOMBRE", nombre);
        dbw.insert("USUARIOS", null, cv); // metodo android*/
    }

    public void clickEliminar(View v) {

        String codigo = etCodigo.getText().toString(); // Recogemos el valor del código
        String sql = "Delete FROM Usuarios WHERE codigo=" + codigo;


        dbw = usdbh.getWritableDatabase(); // para acceder a la base de datos
        //dbw.execSQL(sql); // Ejecuta sentencia sql sin más.

        dbw.delete("Usuarios", "codigo=" + codigo, null);
    }

    public void clickConsultar(View v) {
        dbr = usdbh.getReadableDatabase();
        // Cursor c = dbr.rawQuery("SELECT * FROM Usuarios", null); modo crudo
        Cursor c = dbr.query("Usuarios", new String[]{"codigo", "nombre"}, null, null, null, null, null); // Modo android

        txtResultado.setText("");

        if (c.moveToFirst()) {
            do {
                String codigo = c.getString(0);
                String nombre = c.getString(1);
                txtResultado.append(codigo + " - " + nombre + " \n");
            } while (c.moveToNext());
        }

    }

    public void clickActualizar(View v) {
        String codigo = etCodigo.getText().toString(); // Recogemos el valor del código
        String nombre = etNombre.getText().toString(); // Recogemos el valor del nombre
        String sql = "UPDATE Usuarios SET nombre='" + nombre + "' WHERE codigo=" + codigo;
        dbw = usdbh.getWritableDatabase();

        // para hacerlo con content values:
        /*ContentValues cv = new ContentValues();
        cv.put("NOMBRE", nombre);
        dbw.update("USUARIOS", cv, "CODIGO=" + codigo, null);*/

        dbw.execSQL(sql); // Ejecuta sentencia sql sin más.
    }

}