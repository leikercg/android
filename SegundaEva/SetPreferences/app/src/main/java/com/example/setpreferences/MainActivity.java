package com.example.setpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText nombre, edad;
    TextView mayorEdad;
    Button agregar, finalizar;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<Integer> edades = new ArrayList<>();
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mayorEdad= findViewById(R.id.textViewMayor);
        nombre = findViewById(R.id.editTextNombre);
        edad = findViewById(R.id.editTextEdad);
        agregar = findViewById(R.id.buttonAgregar);
        finalizar = findViewById(R.id.buttonFinalizar);

        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);


        int edadPreferencia = prefs.getInt("edad", 0);
        String nombrePreferencia = prefs.getString("nombre", "Nombre");
        mayorEdad.setText("El mayor es: " + nombrePreferencia + " con " + edadPreferencia + " años");

    }

    public void agregar(View view){
        if (!nombre.getText().toString().isEmpty() && !edad.getText().toString().isEmpty()) {
            nombres.add(nombre.getText().toString());
            edades.add(Integer.parseInt(edad.getText().toString()));
            nombre.setText("");
            edad.setText("");
        }else{
            Toast.makeText(this, "Los campos deben estar completos", Toast.LENGTH_SHORT).show();
        }
    }

    public void finalizar(View view){
        int mayor = edades.get(0);
        int posicion = 0;
        for (int i = 0; i < nombres.size(); i++) {
               if (edades.get(i) > mayor) {
                   mayor = edades.get(i);
                   posicion = i;
               }
        }


        mayorEdad.setText("El mayor es: " + nombres.get(posicion) + " con " + mayor + " años");


        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nombre", nombres.get(posicion));
        editor.putInt("edad", mayor);
        editor.commit();

    }


}