package com.example.listviewejercicio;

import android.content.Intent;
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

public class SecondaryActivity extends AppCompatActivity {

EditText editTextNombre;
EditText editTextNota;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secondary);
        editTextNombre=findViewById(R.id.editTextNombre);
        editTextNota=findViewById(R.id.editTextNota);

        // DAtos pasados por la main
        String nombre = getIntent().getStringExtra("NOMBRE");
        float nota = getIntent().getFloatExtra("NOTA", 0.0f);
        position = getIntent().getIntExtra("POSITION",-1);
        if(position!=-1){
            editTextNombre.setText(nombre);
            editTextNota.setText(String.valueOf(nota));
        }

    }
    public void clickConfirmar(View view){
        String nombre = editTextNombre.getText().toString();
        float nota = Float.parseFloat(editTextNota.getText().toString());

        Intent i = new Intent(); // Creamos un intento

        // Si la posición no es -1, entonces se está editando un alumno existente
        if (position != -1) {
            i.putExtra("NOMBRE", nombre);
            i.putExtra("NOTA", nota);
            i.putExtra("POSITION", position);  // Asegúrate de pasar la posición
        } else {
            // Si la posición es -1, entonces es un nuevo alumno, no pasamos "position"
            i.putExtra("NOMBRE", nombre);
            i.putExtra("NOTA", nota);
        }

        setResult(RESULT_OK, i); // Devuelve el resultado
        finish(); // Finaliza la actividad
    }
}