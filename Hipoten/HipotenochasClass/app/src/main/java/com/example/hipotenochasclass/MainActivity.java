package com.example.hipotenochasclass;

import android.graphics.Color;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DificultadFrament.DificultadSelecionada {

    Point p;
    int altoPantalla, anchoPantalla;
    GridLayout gridLayout;
    ArrayList<Integer> lista;

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
    public void clickElegirDificultad(View v) {
        DificultadFrament fp = new DificultadFrament();
        fp.show(getSupportFragmentManager(), "zzz");
    }

    public void clickNuevoJuego(View v) {
        p = new Point(); //puntero
        Display pantallaDisplay = getWindowManager().getDefaultDisplay(); // Coger las dimensiones de la pantalla que se va a lanzar
        pantallaDisplay.getSize(p); // Se le pasa el punto de la clase point, que será quien almacena el valor.

        // Toast.makeText(this, "Ancho: "+ p.x+" Alto: "+p.y, Toast.LENGTH_SHORT).show();

        altoPantalla = p.y;
        anchoPantalla = p.x;

        gridLayout = findViewById(R.id.grilla_layout);// Inicializado, OJO REVISAR LA CLASE QUE SE IMPORTA
        añadirCeldas(8);
    }



    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu

        if (item.getItemId() == R.id.itemIcono) {

            clickInstruciones(null);
            Toast.makeText(this, "Selecc Icono", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.itemDificultad) {

            clickElegirDificultad(null);
            Toast.makeText(this, "Dificultad", Toast.LENGTH_SHORT).show();

        }else if (item.getItemId() == R.id.itemInstrucciones) {

            Toast.makeText(this, "Instrucciones", Toast.LENGTH_SHORT).show();
            clickInstruciones(null);
        }else if (item.getItemId() == R.id.itemNuevoJuego) {

            clickNuevoJuego(null);
            Toast.makeText(this, "Nuevo juego", Toast.LENGTH_SHORT).show();

        }
        return true;
    }


    public void añadirCeldas(int dimension) {
        int numHipotenochas = 0;

        if(dimension==8){
            numHipotenochas=10;
        } else if (dimension==10) {
            numHipotenochas=30;
        } else if (dimension==16) {
            numHipotenochas=60;
        }
        gridLayout.removeAllViews(); // Limpia las celdas anteriores
        gridLayout.setColumnCount(dimension); // Configura las columnas dinámicamente
        gridLayout.setRowCount(dimension);    // Configura las filas dinámicamente

        lista=new ArrayList<>();
        while(numHipotenochas>=1){
            int random = (int) (Math.random()*dimension*dimension);
            if(lista.size()==0){
            lista.add(random);
            numHipotenochas--;
            } else {
                if (!lista.contains(random)){
                    lista.add(random);
                    numHipotenochas--;
                }
            }

        }

        for (int i = 0; i < dimension*dimension; i++) { // Cambié el número de botones a 64
            Button b = new Button(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(anchoPantalla / dimension, (altoPantalla-200) / dimension);
            b.setLayoutParams(lp);

          //  b.setText(""+i);

            b.setId(View.generateViewId()); // Genera la id del botón automáticamente
            if (lista.contains(i))
                b.setBackgroundColor(Color.rgb(125,24,193));
            b.setOnClickListener(v -> { //evento click
                // Acción para cada botón
                Toast.makeText(this, "Corto", Toast.LENGTH_SHORT).show();
            });

            b.setOnLongClickListener(v -> { //evento click
                // Acción para cada botón
                Toast.makeText(this, "Largo", Toast.LENGTH_SHORT).show();

                return false;
            });

            gridLayout.addView(b); // Añadir el botón al GridLayout
        }
    }

    @Override
    public void nivel(String nivel) { // Que hacer al volver a la main con la interfaz
        if(nivel.compareTo("Amateur")==0){
            p = new Point(); //puntero
            Display pantallaDisplay = getWindowManager().getDefaultDisplay(); // Coger las dimensiones de la pantalla que se va a lanzar
            pantallaDisplay.getSize(p); // Se le pasa el punto de la clase point, que será quien almacena el valor.
            // Toast.makeText(this, "Ancho: "+ p.x+" Alto: "+p.y, Toast.LENGTH_SHORT).show();
            altoPantalla = p.y;
            anchoPantalla = p.x;
            gridLayout = findViewById(R.id.grilla_layout);// Inicializado, OJO REVISAR LA CLASE QUE SE IMPORTA
            añadirCeldas(12);
        } else if (nivel.compareTo("Principiante")==0) {
            p = new Point(); //puntero
            Display pantallaDisplay = getWindowManager().getDefaultDisplay(); // Coger las dimensiones de la pantalla que se va a lanzar
            pantallaDisplay.getSize(p); // Se le pasa el punto de la clase point, que será quien almacena el valor.
            // Toast.makeText(this, "Ancho: "+ p.x+" Alto: "+p.y, Toast.LENGTH_SHORT).show();
            altoPantalla = p.y;
            anchoPantalla = p.x;
            gridLayout = findViewById(R.id.grilla_layout);// Inicializado, OJO REVISAR LA CLASE QUE SE IMPORTA
            añadirCeldas(8);
        } else if (nivel.compareTo("Avanzado")==0) {
            p = new Point(); //puntero
            Display pantallaDisplay = getWindowManager().getDefaultDisplay(); // Coger las dimensiones de la pantalla que se va a lanzar
            pantallaDisplay.getSize(p); // Se le pasa el punto de la clase point, que será quien almacena el valor.
            // Toast.makeText(this, "Ancho: "+ p.x+" Alto: "+p.y, Toast.LENGTH_SHORT).show();
            altoPantalla = p.y;
            anchoPantalla = p.x;
            gridLayout = findViewById(R.id.grilla_layout);// Inicializado, OJO REVISAR LA CLASE QUE SE IMPORTA
            añadirCeldas(16);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true); // Habilita mostrar un ícono
            getSupportActionBar().setLogo(R.mipmap.abstracta_foreground); // Reemplaza con tu icono
            getSupportActionBar().setDisplayUseLogoEnabled(true); // Usa el icono como logo
        }

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
        añadirCeldas(8);

    }


}