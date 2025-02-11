package com.example.progressbarthread;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumber;
    TextView textView;
    private Button btnFactorial;
    private ProgressDialog pDialog;
    private MiTareaAsincronaDialog tarea2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los elementos de la interfaz
        editTextNumber = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textView);
        btnFactorial = findViewById(R.id.buttonFactorial);

        // Configuración del botón para calcular el factorial
        btnFactorial.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el número del EditText
                String input = editTextNumber.getText().toString();

                // Verificamos si el input no está vacío y es un número
                if (!input.isEmpty()) {
                    try {
                        int number = Integer.parseInt(input);
                        // Configuración del ProgressDialog
                        pDialog = new ProgressDialog(MainActivity.this);
                        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pDialog.setMessage("Calculando factorial...");
                        pDialog.setCancelable(true);
                        pDialog.setMax(100); // El máximo de la barra de progreso es 100
                        tarea2 = new MiTareaAsincronaDialog();
                        tarea2.execute(number); // Pasamos el número del EditText
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Por favor ingresa un número válido.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Por favor ingresa un número.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Tarea asincrónica para calcular el factorial y actualizar la barra de progreso
    private class MiTareaAsincronaDialog extends AsyncTask<Integer, Integer, Long> {

        @Override
        protected Long doInBackground(Integer... params) {
            int numero = params[0]; // Número cuyo factorial vamos a calcular
            long resultado = 1;

            // Cálculo del factorial con actualización de la barra de progreso
            for (int i = 1; i <= numero; i++) {
                resultado *= i;
                int progreso = (int) ((i / (float) numero) * 100); // Calculamos el progreso en porcentaje
                publishProgress(progreso); // Actualizamos el progreso
                try {
                    Thread.sleep(1000); // Simulamos un cálculo más lento (puedes quitarlo si lo prefieres más rápido)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Si la tarea ha sido cancelada, salimos del bucle
                if (isCancelled()) break;
            }

            return resultado;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int progreso = values[0];
            pDialog.setProgress(progreso); // Actualizamos la barra de progreso
        }

        @Override
        protected void onPreExecute() {
            // Inicializamos el ProgressDialog
            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    MiTareaAsincronaDialog.this.cancel(true); // Cancelamos la tarea si se cancela el diálogo
                }
            });

            pDialog.setProgress(0); // Inicia la barra de progreso en 0
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Long result) {
            // Cuando se complete el cálculo del factorial, cerramos el ProgressDialog y mostramos el resultado
            pDialog.dismiss();
            // Actualizamos el TextView con el resultado del factorial
            textView.setText("El factorial es: " + result);
        }

        @Override
        protected void onCancelled() {
            // Si la tarea es cancelada, mostramos un mensaje
            Toast.makeText(MainActivity.this, "Cálculo cancelado!", Toast.LENGTH_SHORT).show();
        }
    }
}
