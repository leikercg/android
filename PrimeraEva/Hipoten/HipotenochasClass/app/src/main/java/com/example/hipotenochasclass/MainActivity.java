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
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DificultadFrament.DificultadSelecionada,IconoFragment.IconoInterface {

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
    public void clickSeleccionaIcono(View v){
        IconoFragment fp = new IconoFragment();
        fp.show(getSupportFragmentManager(), "zzz");
    }



    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu

        if (item.getItemId() == R.id.itemIcono) {

            clickSeleccionaIcono(null);
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
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(anchoPantalla / dimension, (anchoPantalla) / dimension);
            b.setLayoutParams(lp);

          // b.setText(""+i);

            //b.setId(View.generateViewId()); // Genera la id del botón automáticamente
            b.setId(i);
            if (lista.contains(i)){
                b.setBackgroundColor(Color.rgb(125,24,193));
            }


            int finalI = i;
            b.setOnClickListener(v -> { //evento click corto
                // Acción para el clic corto
                b.setText(""+contarHipotenochas(dimension, finalI));
            });

            b.setOnLongClickListener(v -> { //evento clic largo
                // Acción para el clic largo
                b.setText("X");

                return true;  // Devuelve 'true' para indicar que el evento ha sido consumido
            });

            gridLayout.addView(b); // Añadir el botón al GridLayout
        }
    }

    public int contarHipotenochas(int dimension, int indice) {
        int izquierda = 0, derecha = 0, arriba = 0, abajo = 0;
        int arribaDerecha = 0, abajoDerecha = 0, arribaIzquierda = 0, abajoIzquierda = 0;

        // Si el índice actual está en la lista, muestra "Muerto" y termina
        if (lista.contains(indice)) {
            Toast.makeText(this, "Muerto", Toast.LENGTH_SHORT).show();
            return 0;
        }

        // Verifica celdas adyacentes dentro de los límites
        // Celda derecha
        if ((indice % dimension != dimension - 1) && lista.contains(indice + 1)) {
            derecha++;
        }
        // Celda izquierda
        if ((indice % dimension != 0) && lista.contains(indice - 1)) {
            izquierda++;
        }
        // Celda abajo
        if ((indice / dimension != dimension - 1) && lista.contains(indice + dimension)) {
            abajo++;
        }
        // Celda arriba
        if ((indice / dimension != 0) && lista.contains(indice - dimension)) {
            arriba++;
        }
        // Celda abajo derecha
        if ((indice % dimension != dimension - 1) && (indice / dimension != dimension - 1) && lista.contains(indice + 1 + dimension)) {
            abajoDerecha++;
        }
        // Celda abajo izquierda
        if ((indice % dimension != 0) && (indice / dimension != dimension - 1) && lista.contains(indice - 1 + dimension)) {
            abajoIzquierda++;
        }
        // Celda arriba derecha
        if ((indice % dimension != dimension - 1) && (indice / dimension != 0) && lista.contains(indice + 1 - dimension)) {
            arribaDerecha++;
        }
        // Celda arriba izquierda
        if ((indice % dimension != 0) && (indice / dimension != 0) && lista.contains(indice - 1 - dimension)) {
            arribaIzquierda++;
        }

        // Suma total de hipotenochas alrededor
        int total = izquierda + derecha + arriba + abajo + arribaDerecha + abajoDerecha + arribaIzquierda + abajoIzquierda;

        // Muestra el total en un Toast
        //Toast.makeText(this, "Hipotenochas cercanas: " + total, Toast.LENGTH_SHORT).show();
        return total;
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
            añadirCeldas(16);
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


    @Override
    public void icono(String icono) {
        Toast.makeText(this, "Desde la main icono "+ icono, Toast.LENGTH_SHORT).show();
    }
}