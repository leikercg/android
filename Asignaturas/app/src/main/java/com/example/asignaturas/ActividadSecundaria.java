package com.example.asignaturas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ActividadSecundaria extends AppCompatActivity {
    TextView idioma, asignaturas;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.actividad_secundaria);

        //TextView para mostrar los datos pasados
        idioma=findViewById(R.id.idioma);
        asignaturas=findViewById(R.id.asignaturas);

        // Llamamos al intento para invocar sus valores.
        Intent i = getIntent();

        // Obtenemos del intento el valor de IDIOMA
        idioma.setText(i.getStringExtra("IDIOMA")); // Establacemos el texto selecionado


        // Obtenemos el valor del array enviado desde la main.
        ArrayList<String> asigSeleccionadas = i.getStringArrayListExtra("ASIGNATURAS");

        //Creamos una cadana de texto para mostrar todo con formato
        String texto ="";


        // Recorremos el array con un bucle for para concatenar las cadenas de su valores
        for (String asig:asigSeleccionadas
             ) {
            texto += asig + "\n"; // Concatenamos cada asignatura en una nueva línea
        }

        // Asignamos el texto concatenado al valor del text view de las asignaturas
        asignaturas.setText(texto);



    }
    public void fin(View view){
        finish(); // Esto cierra la actividad actual y vuelve a la anterior
    }
}
