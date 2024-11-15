package com.example.misdialogos;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SecondaryActivity extends AppCompatActivity {
    Spinner lista;
    String nombreSeleccionado;

    protected void onCreate(Bundle savedInstanceState) { // Al crear la vista

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acivity_secondary); // Indicamos la actividad a la que se refiere


        lista = findViewById(R.id.spinner);
        // Adaptador en JAVA
        String[] datos = new String[]{"Selecciona un nombre", "Leiker", "David", "Castillo", "Guzmán"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        lista.setAdapter(adaptador);
        lista.setSelection(-1);

        //getApplicationContext, por que estamos dentro de un contexto, REvisar esto con mainactivity
        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getApplicationContext(), datos[i], Toast.LENGTH_SHORT).show();
                if (i != 0) {
                    Toast.makeText(SecondaryActivity.this, datos[i], Toast.LENGTH_SHORT).show();
                    nombreSeleccionado=datos[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void clickConfirmar(View view){
        Toast.makeText(this, nombreSeleccionado+" ha sido selccionado", Toast.LENGTH_SHORT).show();
    }
}
