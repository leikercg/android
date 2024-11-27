package com.example.ahorcado;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> listaPalabras = new ArrayList<>();
    ArrayList<String> listaLetras;
    ArrayList<String> intentos;
    String palabraSeleccionada;
    Spinner spinnerLetras;
    String letraSeleccionada;
    ImageView imagenAhoracado;
    Button botonAceptar;


    int contadorFallos = 0;
    TextView palabra;
    TextView tvIntentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Agregar palabras a la lista de palabras
        listaPalabras.add("LEIKER");
        listaPalabras.add("CASTILLO");
        listaPalabras.add("GUZMAN");
        listaPalabras.add("DAVID");
        intentos = new ArrayList<>();

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        palabra = findViewById(R.id.palabra);
        tvIntentos = findViewById(R.id.textViewIntentos);
        imagenAhoracado = findViewById(R.id.imageView2);
        botonAceptar=findViewById(R.id.buttonAceptar);

        palabraSeleccionada = listaPalabras.get((int) (Math.random() * 4));
        Toast.makeText(this, palabraSeleccionada, Toast.LENGTH_SHORT).show();

        // Pasamos el adaptador al spinner
        spinnerLetras = findViewById(R.id.spinner); // Iniciar el spinner
        listaLetras = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ñ"));
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaLetras);
        spinnerLetras.setAdapter(adaptador);

        palabra.setText("");
        for (int i = 0; i < palabraSeleccionada.length(); i++) {
            palabra.append("_ ");
        }


        // Indicamos que hacer cuando se selecciona un elemento
        spinnerLetras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getApplicationContext(), datos[i], Toast.LENGTH_SHORT).show();

                letraSeleccionada = listaLetras.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    //////////////////////////// METODOS A USAR/////////
    public void clickAceptar(View v) {

        if (!palabraSeleccionada.contains(letraSeleccionada)&&  !intentos.contains(letraSeleccionada)) { //si no contiene la letra sube el fallo
            contadorFallos++;
            Toast.makeText(this, "" + contadorFallos, Toast.LENGTH_SHORT).show();
        }



        String nuevaPalabra = ""; // Creamos una nueva palabra vacia
        if (!intentos.contains(letraSeleccionada)) { // Si no está aádida a los intentos añadirla
            intentos.add(letraSeleccionada);


            //Errores
            if (contadorFallos == 1) {
                imagenAhoracado.setImageResource(R.mipmap.uno_foreground);
            } else if (contadorFallos == 2) {
                imagenAhoracado.setImageResource(R.mipmap.dos_foreground);
            } else if (contadorFallos == 3) {
                imagenAhoracado.setImageResource(R.mipmap.tres_foreground);
            } else if (contadorFallos == 4) {
                imagenAhoracado.setImageResource(R.mipmap.cuatro_foreground);
            } else if (contadorFallos == 5) {
                imagenAhoracado.setImageResource(R.mipmap.cinco_foreground);
            } else if (contadorFallos == 6) {
                imagenAhoracado.setImageResource(R.mipmap.seis_foreground);

                /*new AlertDialog.Builder(this)
                        .setTitle("Fin de la partida")
                        .setMessage("¡Has perdido! La palabra era: " + palabraSeleccionada)
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            // Realiza alguna acción, como reiniciar el juego
                        })
                        .show();*/




                PartidaFinalFragment partidaFinalFragment=new PartidaFinalFragment(); //declarar un fragment


                Bundle args = new Bundle();
                args.putString("palabraSeleccionada", palabraSeleccionada); // Pasar la palabra
                args.putInt("contadorFallos", contadorFallos); // Pasar el número de fallos

                // Establecer los argumentos en el fragmento
                partidaFinalFragment.setArguments(args);

                partidaFinalFragment.show(getSupportFragmentManager(), "xxx"); // lanzarlo

                botonAceptar.setEnabled(false);
                recreate();

            }
        }


        // Recorrer cada letra de la palabra seleccionada
        for (int i = 0; i < palabraSeleccionada.length(); i++) {
            char letraActual = palabraSeleccionada.charAt(i); //Al ser de longitud 1 esto sirve

            // Si la letra de la palabra secreta esta dentro de los intentos, añadirla al resultado
            if (intentos.contains(String.valueOf(letraActual))) {
                nuevaPalabra += letraActual + " ";
            } else {
                // Si no, añadir un guion bajo
                nuevaPalabra += "_ ";
            }
        }


        //Mostrar todos los intentos
        tvIntentos.setText("");
        for (String intento : intentos) {
            tvIntentos.append(intento + ", ");
        }

        // Mostrar la palabra actualizada
        palabra.setText(nuevaPalabra);


    }
}