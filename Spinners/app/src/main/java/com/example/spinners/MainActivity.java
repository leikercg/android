package com.example.spinners;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Spinner lista;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.spinner);
        tv = findViewById(R.id.tv);


       // Adaptador en JAVA
        String[] datos = new String[]{"Elem1", "Elem2", "Elem3", "Elem4", "Elem5"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
        lista.setAdapter(adaptador);


        // Adaptador desde Recursos
        /*
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.datos_lista, android.R.layout.simple_list_item_1);
        lista.setAdapter(adaptador);*/

        //getApplicationContext, por que estamos dentro de un contexto, REvisar esto con mainactivity
        lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), datos[i], Toast.LENGTH_SHORT).show();
                tv.setText(datos[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}