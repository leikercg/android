package com.example.examen;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CapicuaActivity extends AppCompatActivity {

    TextView textViewNumero;
    Button btnComprobar;
    protected void onCreate(Bundle savedInstanceState) { // Al crear la vista

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.capicua_activity); // Indicamos la actividad a la que se refiere

        btnComprobar=findViewById(R.id.buttonVerCapicua);
        textViewNumero=findViewById(R.id.editTextNumber);




    }

    public void comprobar(View view){
        int numero = Integer.parseInt(textViewNumero.getText().toString());
        int numeroOriginal = numero;
        int numeroRevertido = 0;

        while (numero > 0) {
            int digito = numero % 10;
            numeroRevertido = numeroRevertido * 10 + digito;
            numero = numero / 10;
        }
        if (numeroRevertido == numeroOriginal) {
            Toast.makeText(this, "Es capicua", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No es capicua", Toast.LENGTH_SHORT).show();
        }
    }

}


