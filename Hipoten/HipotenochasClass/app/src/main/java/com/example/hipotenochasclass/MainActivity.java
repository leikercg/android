package com.example.hipotenochasclass;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;

public class MainActivity extends AppCompatActivity {

    Point p;
    int altoPantalla, anchoPantalla;
    GridLayout gridLayout;

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_principaal, menu); //

        return true;

        //getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true; esto es del PDF
    }
    public void clickInstruciones(View v) {
        InstruccionesFragment fp = new InstruccionesFragment();
        fp.show(getSupportFragmentManager(), "zzz");

    }
    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu

        if (item.getItemId() == R.id.itemIcono) {

            clickInstruciones(null);
            Toast.makeText(this, "Selecc Icono", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.itemDificultad) {

            Toast.makeText(this, "Dificultad", Toast.LENGTH_SHORT).show();

        }else if (item.getItemId() == R.id.itemInstrucciones) {

            Toast.makeText(this, "Instrucciones", Toast.LENGTH_SHORT).show();
            clickInstruciones(null);
        }else if (item.getItemId() == R.id.itemNuevoJuego) {

            Toast.makeText(this, "Nuevo juego", Toast.LENGTH_SHORT).show();

        }
        return true;
    }

    public void añadirCeldas(int dimension, int anchoPantalla, int altoPantalla) {
        for (int i = 0; i < 64; i++) { // Cambié el número de botones a 64
            Button b = new Button(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(anchoPantalla / dimension, altoPantalla / dimension);
            b.setLayoutParams(lp);

            b.setText("Btn " + i);

            b.setId(View.generateViewId()); // Genera la id del botón automáticamente
            b.setOnClickListener(v -> {
                // Acción para cada botón
                Toast.makeText(this, "Botón  clickeado", Toast.LENGTH_SHORT).show();
            });
            gridLayout.addView(b); // Añadir el botón al GridLayout
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        p = new Point(); //puntero
        Display pantallaDisplay = getWindowManager().getDefaultDisplay(); // Coger las dimensiones de la pantalla que se va a lanzar
        pantallaDisplay.getSize(p); // Se le pasa el punto de la clase point, que será quien almacena el valor.

        // Toast.makeText(this, "Ancho: "+ p.x+" Alto: "+p.y, Toast.LENGTH_SHORT).show();

        altoPantalla = p.y;
        anchoPantalla = p.x;

        gridLayout = findViewById(R.id.grilla_layout);// Inicializado, OJO REVISAR LA CLASE QUE SE IMPORTA


        añadirCeldas(8,anchoPantalla,altoPantalla);

    }
}