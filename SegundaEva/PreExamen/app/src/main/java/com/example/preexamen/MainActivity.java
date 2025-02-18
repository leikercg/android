package com.example.preexamen;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button button1, button2, button3, button4, buttonJugar;
    ArrayList<Button> botones = new ArrayList<>();
    int[] soluciones = new int[4];
    // Si no se usan, puedes eliminar "intentos"
    int[] intentos = new int[4];
    int puntosMaquina = 0;
    int puntosJugador = 0;
    // Variable para llevar la posición actual de la secuencia que se debe comprobar
    int indiceSecuencia = 0;
    TextView textViewGlobalMaquina, textViewGlobalJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar los botones con su ID
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        buttonJugar = findViewById(R.id.buttonJugar);

        // Enlazar los TextView para mostrar puntos
        textViewGlobalMaquina = findViewById(R.id.textViewGlobalMaquina);
        textViewGlobalJugador = findViewById(R.id.textViewGlobalJugador);

        // Setear el color inicial de los botones (usamos un código hexadecimal para el gris)
        button1.setBackgroundColor(Color.parseColor("#808080"));
        button2.setBackgroundColor(Color.parseColor("#808080"));
        button3.setBackgroundColor(Color.parseColor("#808080"));
        button4.setBackgroundColor(Color.parseColor("#808080"));

        // Agregar los botones a la lista
        botones.add(button1);
        botones.add(button2);
        botones.add(button3);
        botones.add(button4);

        // Deshabilitamos los botones de la secuencia hasta que se pulse "Jugar"
        disableBotones();

        // Inicializar la visualización de puntos
        updatePointsDisplay();
    }

    // Función que se llama al pulsar cualquiera de los botones (button1 a button4)
    // Se comprueba el orden correcto de la secuencia.
    public void comprobar(View view) {
        int indicePulsado = botones.indexOf((Button) view); // Obtener el índice del botón pulsado

        // Comprobar si el botón pulsado coincide con el siguiente botón esperado en la secuencia
        if (soluciones[indiceSecuencia] == indicePulsado) {
            Toast.makeText(this, "¡Acertaste en el orden correcto!", Toast.LENGTH_SHORT).show();
            indiceSecuencia++;  // Se avanza en la secuencia

            // Si se completó la secuencia (4 aciertos en orden)
            if (indiceSecuencia == soluciones.length) {
                Toast.makeText(this, "Has ganado un punto", Toast.LENGTH_SHORT).show();
                puntosJugador++;
                resetearSecuencia();
            }
        } else {
            // Error en el orden
            Toast.makeText(this, "Fallaste, has perdido un punto", Toast.LENGTH_SHORT).show();
            puntosMaquina++;
            resetearSecuencia();
        }

        // Actualizamos la visualización de puntos
        updatePointsDisplay();

        // Comprobación final de puntos para determinar ganador o perdedor
        if (puntosMaquina == 3) {
            Toast.makeText(this, "Has perdido la partida", Toast.LENGTH_SHORT).show();
            resetearJuego();
        } else if (puntosJugador == 3) {
            Toast.makeText(this, "Has ganado la partida", Toast.LENGTH_SHORT).show();
            resetearJuego();
        }
    }

    // Inicia una nueva jugada
    public void jugar(View view) {
        resetearSecuencia(); // Reinicia la secuencia antes de iniciar la jugada
        disableBotones();    // Deshabilitamos los botones mientras se muestra la secuencia
        MiTareaAsincrona tarea = new MiTareaAsincrona();
        tarea.execute();
    }

    // Reinicia la secuencia: reinicializa el array de soluciones y el contador de aciertos (índiceSecuencia)
    private void resetearSecuencia() {
        soluciones = new int[4]; // Se reinicia el array (todos los valores se ponen a 0)
        indiceSecuencia = 0;      // Se reinicia el índice de comprobación
    }

    // Reinicia el juego: puntos y secuencia
    private void resetearJuego() {
        puntosMaquina = 0;
        puntosJugador = 0;
        resetearSecuencia();
        updatePointsDisplay();
        Toast.makeText(this, "Juego reiniciado", Toast.LENGTH_SHORT).show();
    }

    // Actualiza los TextView con los puntos actuales
    private void updatePointsDisplay() {
        textViewGlobalMaquina.setText("Maquina: " + puntosMaquina);
        textViewGlobalJugador.setText("Jugador: " + puntosJugador);
    }

    // Deshabilita los botones de la secuencia
    private void disableBotones() {
        for (Button btn : botones) {
            btn.setEnabled(false);
        }
    }

    // Habilita los botones de la secuencia
    private void enableBotones() {
        for (Button btn : botones) {
            btn.setEnabled(true);
        }
    }

    // Tarea asíncrona para mostrar la secuencia de colores aleatorios
    class MiTareaAsincrona extends AsyncTask<Void, Integer, Void> {
        Random rand = new Random();

        @Override
        protected Void doInBackground(Void... voids) {
            // Genera 4 pasos de la secuencia
            for (int i = 0; i < 4; i++) {
                int aleatorio = rand.nextInt(botones.size()); // Selecciona un botón aleatorio
                int color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)); // Genera un color aleatorio

                publishProgress(aleatorio, color); // Cambia el color al botón seleccionado
                SystemClock.sleep(1000); // Espera 1 segundo
                publishProgress(aleatorio, Color.parseColor("#808080")); // Vuelve el botón a su color original (gris)

                soluciones[i] = aleatorio; // Guarda el índice del botón en la secuencia
            }

            Log.d("Soluciones", Arrays.toString(soluciones));
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // Actualiza la UI: cambia el color del botón en el índice indicado
            botones.get(values[0]).setBackgroundColor(values[1]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Al finalizar la secuencia, habilitamos los botones para que el usuario pueda pulsarlos.
            enableBotones();
        }
    }
}
