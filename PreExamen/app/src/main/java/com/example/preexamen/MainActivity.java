package com.example.preexamen;

import android.os.AsyncTask;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView button1, button2, button3, button4;
    Button buttonAprende, buttonJugar;
    TextView textView;

    ImageView[] buttons;
    // Lista de colores posibles para los destellos
    int[] colores = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.DKGRAY};
    int[] orden = new int[4]; // Para almacenar el orden de los destellos
    int[] secuenciaJugador = new int[4]; // Para almacenar la secuencia del jugador
    int currentPlayerStep = 0; // Para llevar un registro de en qué paso está el jugador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAprende = findViewById(R.id.buttonAprende);
        buttonJugar = findViewById(R.id.buttonJugar);
        button1 = findViewById(R.id.imageView);
        button2 = findViewById(R.id.imageView2);
        button3 = findViewById(R.id.imageView3);
        button4 = findViewById(R.id.imageView4);
        textView = findViewById(R.id.textView);

        buttons = new ImageView[]{button1, button2, button3, button4};
        botonesIniciales();

        // Configuramos los botones para que el jugador pueda hacer clic en ellos
        setButtonListeners();
    }

    public void jugar(View view) {
        // Generar una secuencia aleatoria de colores
        for (int i = 0; i < 4; i++) {
            orden[i] = new Random().nextInt(colores.length);  // Colores aleatorios
        }
        new IluminarBotonesTask().execute();
    }

    private void setButtonListeners() {
        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            buttons[i].setOnClickListener(v -> {
                secuenciaJugador[currentPlayerStep] = index;  // Guardamos el paso actual del jugador
                currentPlayerStep++;  // Avanzamos al siguiente paso

                // Comprobamos si la secuencia es correcta
                if (currentPlayerStep == orden.length) {
                    checkPlayerSequence();
                }
            });
        }
    }

    private void checkPlayerSequence() {
        boolean correct = true;
        // Comparamos la secuencia del jugador con la generada
        for (int i = 0; i < orden.length; i++) {
            if (secuenciaJugador[i] != orden[i]) {
                correct = false;
                break;
            }
        }

        // Mostrar el resultado al jugador
        if (correct) {
            textView.setText("¡Has acertado!");
        } else {
            textView.setText("¡Incorrecto! Intenta de nuevo.");
        }

        // Resetear el turno
        currentPlayerStep = 0;
    }

    // AsyncTask para iluminar los botones con un retraso
    class IluminarBotonesTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                // Seleccionamos un botón aleatorio
                int botonAleatorio = random.nextInt(4); // Un número entre 0 y 3

                // Seleccionamos un color aleatorio
                int colorAleatorio = orden[botonAleatorio]; // Usamos la secuencia generada

                // Cambiar el color del botón aleatorio con el color aleatorio
                publishProgress(botonAleatorio, colorAleatorio);

                try {
                    Thread.sleep(1000);  // Tiempo de destello de 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Apagar el color (volver a gris)
                publishProgress(-1);

                try {
                    Thread.sleep(1000);  // Tiempo de pausa de 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            // Al inicio, aseguramos que los botones estén en gris
            botonesIniciales();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[0] != -1) {
                // Iluminar el botón correspondiente con el color seleccionado
                buttons[values[0]].setBackgroundColor(colores[values[1]]);
            } else {
                // Apagar todos los botones (volver a gris)
                botonesIniciales();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textView.setText("Tu turno");
        }
    }

    private void botonesIniciales() {
        // Asignamos los colores originales (gris) a las ImageViews
        button1.setBackgroundColor(Color.GRAY);
        button2.setBackgroundColor(Color.GRAY);
        button3.setBackgroundColor(Color.GRAY);
        button4.setBackgroundColor(Color.GRAY);
    }
}
