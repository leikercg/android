package com.example.hipotenochas;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FragmentoConfiguracion.TamanyoLayout {

    Point p;
    int altoPantalla, anchoPantalla;
    GridLayout gridLayout;
    int tamanyoGrid = 1;
    int columnas, filas;
    int[] tablero;
    int hipotenochasRestantes;


    //IMPORTANTE MENÚ -> Editar el themes.xml y quitarle el NoActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //Método para crear el menú
        MenuInflater menuInflater = getMenuInflater(); //Obtener el menú
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p = new Point();
        Display pantallaDisplay = getWindowManager().getDefaultDisplay(); //Se obtiene la pantalla
        pantallaDisplay.getSize(p);
        anchoPantalla = p.x;
        altoPantalla = p.y;

        gridLayout = findViewById(R.id.grid_layout);

        calcularGrid(tamanyoGrid); //Método para calcular el tamaño del tablero
        aniadirBotones(); //método para añadir botones al tablero
    }

    @Override //Método para manejar los botones del menú
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.item_Instrucciones) {
            FragmentoInstrucciones fragmentoInstrucciones = new FragmentoInstrucciones();
            FragmentManager fm = getSupportFragmentManager();
            fragmentoInstrucciones.show(fm, "fragmentoInstrucciones");
        } else if (item.getItemId() == R.id.item_ConfiguraJuego) {
            FragmentoConfiguracion fragmentoConfiguracion = new FragmentoConfiguracion();
            FragmentManager fm = getSupportFragmentManager();
            fragmentoConfiguracion.show(fm, "fragmentoConfiguracion");
        } else if (item.getItemId() == R.id.item_SeleccionPersonaje) {
            FragmentoSeleccion fragmentoSeleccion = new FragmentoSeleccion();
            FragmentManager fm = getSupportFragmentManager();
            fragmentoSeleccion.show(fm, "fragmentoSeleccion");
        } else if(item.getItemId() == R.id.item_NuevoJuego) {
            gridLayout.removeAllViews(); //Así elimino los botones del tablero
            aniadirBotones(); //Y creo nuevos botones
            Toast.makeText(this, "¡Nuevo juego!", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void aniadirBotones() {
        for (int i = 0; i < columnas * filas; i++) {
            Button b = new Button(this); //Creo un botón
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(anchoPantalla/columnas, altoPantalla/(filas + 1)); //Establecemos el tamaño de filas y columnas
            b.setLayoutParams(lp); //Con esto puedo conseguir el tamaño del botón
            b.setTag(i); //La posición de la casilla con el tag
            b.setId(View.generateViewId()); //y se genera un id para el botón
            b.setOnClickListener(new View.OnClickListener() { //y este método maneja el click
                @Override
                public void onClick(View view) {
                    int posicion = (int) view.getTag(); //Obtenemos la posición de la casilla
                    manejarClick(posicion, (Button) view);
                }
            });
            b.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Button b = (Button) view;
                    if(b.getResources() != null) {
                        hipotenochasRestantes--;
                    }
                    b.setBackgroundColor(Color.BLACK);
                    return true;
                }
            });
            gridLayout.addView(b);
        }

        //Colocar hipotenochas en el tablero
        tablero = new int[columnas * filas];
        hipotenochasRestantes = 10;

        for(int i = 0; i < hipotenochasRestantes; i++) {
            int randomPosition = (int) (Math.random() * (columnas * filas));
            while(tablero[randomPosition] == 1) { //Nos aseguramos que no haya ya una hipotenocha
                randomPosition = (int) (Math.random() * (columnas * filas));
            }
            tablero[randomPosition] = 1;
        }
    }

    public void manejarClick(int posicion, Button boton) {
        if(tablero[posicion] == 1) { //Si la casilla contiene una hipotenocha
            boton.setBackgroundResource(R.drawable.hipotenocha); //Muestra imagen de la hipotenocha
            boton.setEnabled(false);
            Toast.makeText(this, "¡Has perdido! Has pulsado en una hipotenocha", Toast.LENGTH_LONG).show(); //Mostrar mensaje derrota
            //Desactivar botones
            desactivarBotones();
        } else {
            // Si el botón no tiene hipotenocha contamos cuantas tenemos alrededor
            int minasCercanas = contarMinasCercanas(posicion);
            boton.setText(String.valueOf(minasCercanas));
            boton.setEnabled(false);

            //Comprobamos si el jugador ha ganado
            if(comprobarVictoria()) {
                Toast.makeText(this, "¡Has ganado! Has encontrado todas las hipotenochas", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean comprobarVictoria() {
        return true;
    }

    public int contarMinasCercanas(int posicion) {
        int minasCercanas = 0;
        int tamanyo = 8;
        int[] direcciones = {-1, 1, -tamanyo, tamanyo, -tamanyo -1, -tamanyo + 1, tamanyo -1, tamanyo +1};

        for(int dir: direcciones) {
            int minaVecino = posicion + dir; //Obtenemos la posicion del vecino
            if(minaVecino >= 0 && minaVecino < tablero.length && tablero[minaVecino] == 1) {
                minasCercanas++;
            }
        }
        return minasCercanas;
    }

    public void calcularGrid(int i) {
        if(tamanyoGrid != i) {
            tamanyoGrid = i;
        }
        columnas = 4 * (tamanyoGrid + 1);
        filas = 4 * (tamanyoGrid + 1);
        gridLayout.setRowCount(filas);
        gridLayout.setColumnCount(columnas);
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        b.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void tamanyoSeleccionado(int i) {
        gridLayout.removeAllViews(); //IMPORTANTE PARA QUE NO SE SUPERPONGAN LOS BOTONES
        calcularGrid(i);

    }

    //Desactiva todos los botones al acabar el juego
    public void desactivarBotones() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button boton = (Button) gridLayout.getChildAt(i);
            boton.setEnabled(false);
        }
    }
}