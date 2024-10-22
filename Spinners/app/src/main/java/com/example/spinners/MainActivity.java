package com.example.spinners;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    EditText num1, num2; // son los numero introducidos
    Button boton; // Es el boton de calcular


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lista = findViewById(R.id.spinner);
        tv = findViewById(R.id.tv);

        num1=findViewById(R.id.num1);
        num2=findViewById(R.id.num2);
        boton= findViewById(R.id.button);


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
                // Toast.makeText(getApplicationContext(), datos[i], Toast.LENGTH_SHORT).show();

                tv.setText(datos[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_principal, menu);

        return true;

        //getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true; esto es del PDF
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.opcion_sumar) {
            tv.setText("SUMAR");
        } else if (item.getItemId() == R.id.opcion_restar) {
            tv.setText("RESTAR");
        } else if (item.getItemId() == R.id.opcion_multiplicar) {
            tv.setText("MULTIPLICAR");
        } else if (item.getItemId() == R.id.opcion_dividir) {
            tv.setText("DIVIDIR");
        }
        return true;
    }

}