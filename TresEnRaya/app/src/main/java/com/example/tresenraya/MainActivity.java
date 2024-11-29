package com.example.tresenraya;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GanadorFragment.DatoNombre {
    Point p;
    GridLayout grilla;
    ArrayList<int[]> soluciones = new ArrayList<>();
    Button btnReiniciar;
    int[] solucion1 = {0, 1, 2};
    int[] solucion2 = {3, 4, 5};
    int[] solucion3 = {6, 7, 8};
    int[] solucion4 = {0, 3, 6};
    int[] solucion5 = {1, 4, 7};
    int[] solucion6 = {2, 5, 8};
    int[] solucion7 = {0, 4, 8};
    int[] solucion8 = {2, 4, 6};


    ArrayList<Integer> jugadasHumano = new ArrayList<>();
    ArrayList<Integer> jugadasMaquina = new ArrayList<>();

    boolean turnoHumano = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        soluciones.add(new int[]{0, 1, 2}); // Fila 1
        soluciones.add(new int[]{3, 4, 5}); // Fila 2
        soluciones.add(new int[]{6, 7, 8}); // Fila 3
        soluciones.add(new int[]{0, 3, 6}); // Columna 1
        soluciones.add(new int[]{1, 4, 7}); // Columna 2
        soluciones.add(new int[]{2, 5, 8}); // Columna 3
        soluciones.add(new int[]{0, 4, 8}); // Diagonal
        soluciones.add(new int[]{2, 4, 6}); // Diagonal


        btnReiniciar = findViewById(R.id.buttonReiniciar);
        grilla = findViewById(R.id.grilla);

        for (int i = 0; i < 9; i++) { // 3x3 Grid
            Button b = new Button(this);

            // Configurar las propiedades del botón
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0; // 0 para habilitar "peso"
            params.height = 0; // 0 para habilitar "peso"
            params.rowSpec = GridLayout.spec(i / 3, 1, 1f); // Fila
            params.columnSpec = GridLayout.spec(i % 3, 1, 1f); // Columna
            b.setLayoutParams(params);

            b.setText("Btn " + (i + 1)); // Asignar texto
            b.setId(View.generateViewId());
            b.setOnClickListener(this);

            b.setTag(i);

            grilla.addView(b); // Añadir el botón al GridLayout
        }


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonReiniciar) {
            reiniciarJuego();
        } else {
            if (turnoHumano) {
               // Toast.makeText(this, "Turno humano", Toast.LENGTH_SHORT).show();

                Button botonPulsado = (Button) view;
                turnoHumano = false;
                botonPulsado.setText("X");
                botonPulsado.setEnabled(false);
                jugadasHumano.add((int) botonPulsado.getTag());
            } else {
              //  Toast.makeText(this, "Turno máquina", Toast.LENGTH_SHORT).show();
                Button botonPulsado = (Button) view;
                turnoHumano = true;
                botonPulsado.setText("O");
                botonPulsado.setEnabled(false);
                jugadasMaquina.add((int) botonPulsado.getTag());
            }
            comprobarJugadas();
        }
    }

    public void comprobarJugadas() {

        // Verificar si el humano ha ganado
        for (int[] solucion : soluciones) {
            // Comprobar si todas las posiciones de una solución están llenas con "X"
            if (jugadasHumano.contains(solucion[0]) && jugadasHumano.contains(solucion[1]) && jugadasHumano.contains(solucion[2])) {
                // Si todas las posiciones de la solución están ocupadas por el humano, el humano ha ganado
                inhabilitarBotones();
                return;
            }
        }

        // Verificar si la máquina ha ganado
        for (int[] solucion : soluciones) {
            // Comprobar si todas las posiciones de una solución están llenas con "O"
            if (jugadasMaquina.contains(solucion[0]) && jugadasMaquina.contains(solucion[1]) && jugadasMaquina.contains(solucion[2])) {
                // Si todas las posiciones de la solución están ocupadas por la máquina, la máquina ha ganado
                inhabilitarBotones();
                return;
            }
        }

        // Si no hay ganador, comprobar si el tablero está lleno (empate)
        if (jugadasHumano.size() + jugadasMaquina.size() == 9) {
            Toast.makeText(this, "¡Empate!", Toast.LENGTH_SHORT).show();
        }
    }

    // Función para reiniciar el juego
    private void reiniciarJuego() {
        // Limpiar las jugadas
        // jugadasHumano.clear();
        // jugadasMaquina.clear();

        // Limpiar los botones en el GridLayout
       /* for (int i = 0; i < grilla.getChildCount(); i++) {
            Button boton = (Button) grilla.getChildAt(i);
            boton.setText("");
            boton.setEnabled(true); // Hacer que los botones sean clickeables de nuevo
        }*/

        // finish();  // Finaliza la actividad actual
        recreate();

    }
    public void inhabilitarBotones(){
        grilla=findViewById(R.id.grilla);
        for (int i = 0; i < grilla.getChildCount(); i++) {
            Button boton = (Button) grilla.getChildAt(i);
            boton.setEnabled(false); // Hacer que los botones sean clickeables de nuevo
        }

        GanadorFragment gf= new GanadorFragment();
        gf.show(getSupportFragmentManager(),"dd");
    }

    @Override
    public void pasarNombre(String nombre) {///Metodo de la interfaz
        Toast.makeText(this, "Tu nombre es: "+nombre, Toast.LENGTH_SHORT).show();
    }
}