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
                Toast.makeText(MainActivity.this, "nada seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        tv.setText("Operación");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_principal, menu);

        return true;

        //getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true; esto es del PDF
    }

    public void calcular(View v) {
        if (tv.getText().toString().equalsIgnoreCase("elem1")){ // Por que reutiñizamos el texto que fija el spinner
            Toast.makeText(this, "Por favor, selecciona una operación primero", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            int numero1 = Integer.parseInt(num1.getText().toString());
            int numero2 = Integer.parseInt(num2.getText().toString());
            int resultado = 0;

            // Comparaciones de cadena con if-else para realizar la operación
            String operacion = tv.getText().toString();
            if (operacion.equals("SUMAR")) {
                resultado = numero1 + numero2;
            } else if (operacion.equals("RESTAR")) {
                resultado = numero1 - numero2;
            } else if (operacion.equals("MULTIPLICAR")) {
                resultado = numero1 * numero2;
            } else if (operacion.equals("DIVIDIR")) {
                if (numero2 != 0) {
                    resultado = numero1 / numero2;
                } else {
                    Toast.makeText(this, "No se puede dividir por cero", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Toast.makeText(this, String.valueOf(resultado), Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingresa números válidos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.opcion_sumar) {
            tv.setText("SUMAR");
            boton.setText("+");
        } else if (item.getItemId() == R.id.opcion_restar) {
            tv.setText("RESTAR");
            boton.setText("-");
        } else if (item.getItemId() == R.id.opcion_multiplicar) {
            tv.setText("MULTIPLICAR");
            boton.setText("x");
        } else if (item.getItemId() == R.id.opcion_dividir) {
            tv.setText("DIVIDIR");
            boton.setText("/");
        }
        return true;
    }

}