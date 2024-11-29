package com.example.examen;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ColoresActivity extends AppCompatActivity {
// Declaramos los textos
    TextView arribaIzquierda, arribaDerecha, abajoIzquierda, abajoDerecha;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.colores_activity);

        // Inicializamos
        arribaIzquierda = findViewById(R.id.textView1);
        arribaDerecha = findViewById(R.id.textView2);
        abajoIzquierda = findViewById(R.id.textView4);
             abajoDerecha = findViewById(R.id.textView3);
    }


    public void giroHorario(View view) {

        //guardamos los valores viejos
        String viejo1 = arribaIzquierda.getText().toString();
        String viejo2 = arribaDerecha.getText().toString();
        String viejo3 = abajoDerecha.getText().toString();
        String viejo4 = abajoIzquierda.getText().toString();
        //los intercambiamos
        arribaIzquierda.setText(viejo4);
        arribaDerecha.setText(viejo1);
        abajoDerecha.setText(viejo2);
        abajoIzquierda.setText(viejo3);
    }

    public void giroAntiHorario(View view) {

        //guardamos los valores viejos
        String viejo1 = arribaIzquierda.getText().toString();
        String viejo2 = arribaDerecha.getText().toString();
        String viejo3 = abajoDerecha.getText().toString();
        String viejo4 = abajoIzquierda.getText().toString();

        //los intercambiamos
        arribaIzquierda.setText(viejo2);
        arribaDerecha.setText(viejo3);
        abajoDerecha.setText(viejo4);
        abajoIzquierda.setText(viejo1);
    }
}
