package com.example.bdrecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AltaActivity extends AppCompatActivity {
    EditText etNombre, etEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alta);

        etNombre = findViewById(R.id.editTextNombre);
        etEdad = findViewById(R.id.editTextEdad);

        String nombre = getIntent().getStringExtra("NOMBRE");
        int edad = getIntent().getIntExtra("EDAD", 0);
        if (edad != 0) {
            etNombre.setText(nombre);
            etEdad.setText(edad + ""); // Convertir a string para que no pete
        }
    }

    public void clickVolver(View view) {
        Intent i = new Intent();
        i.putExtra("NOMBRE", etNombre.getText().toString());
        i.putExtra("EDAD", etEdad.getText().toString());

        // comparar con cadena vacia, con equals, no con null
        if (etEdad.getText().toString().equals("") || etEdad.getText().toString().equals("")) { // si un campo está vacio no devolver nada
            setResult(RESULT_CANCELED,i);
        } else {
            setResult(RESULT_OK, i); // devolvemos esto
        }
        finish();
    }
}