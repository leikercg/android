package com.example.mayor_edad;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActividadSecundaria extends AppCompatActivity {
    TextView edad_secondary, nombre_secondary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acivity_secondary);

        // Obtener los valores del Intent-
        Intent i = getIntent();
        String nombre = i.getStringExtra("NOMBRE");
        int edad = i.getIntExtra("EDAD", 0);
        ArrayList <Integer> numeros = new ArrayList<>();

        // Enlazar las vistas del layout
        edad_secondary = findViewById(R.id.edad_secondary);
        nombre_secondary = findViewById(R.id.nombre_secodary);

        // Usar String.valueOf() para convertir el int a String
        edad_secondary.setText(String.valueOf(edad));  // Conversión del int a String
        nombre_secondary.setText(nombre);
    }
}
