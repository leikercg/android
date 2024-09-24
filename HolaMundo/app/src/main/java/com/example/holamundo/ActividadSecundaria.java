package com.example.holamundo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;

public class ActividadSecundaria extends Activity {
    TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary);
        texto = findViewById(R.id.texto);

        Intent i = getIntent();
        String nombre = "Hola "+ i.getStringExtra("NOMBRE");
        texto.setText(nombre);
    }
}
