package com.example.miprimeraaplicacion;

import android.os.Bundle;
import android.view.View; // Importar View
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast; // Importar Toast

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity //implements View.OnClickListener // Solo si implementamos clase
{
    Button botoncito;
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        botoncito = findViewById(R.id.botoncito);
        texto = findViewById(R.id.texto);

       // botoncito.setOnClickListener(this); // Para implementar la clase MainActivityMain

        /*botoncito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Acción al hacer clic
                texto.setText("VIVA LA VIDA");
                botoncito.setText("Botón pulsado");
                botoncito.setEnabled(false);
                Toast.makeText(MainActivity.this, "Botón clicado!", Toast.LENGTH_SHORT).show();
            }
        }); */

    }

    public void pulsar(View view) {
        texto.setText("VIVA LA VIDA");
        botoncito.setText("Botón pulsado");
        botoncito.setEnabled(false);
        Toast.makeText(MainActivity.this, "Botón clicado!", Toast.LENGTH_SHORT).show();
    }
}
