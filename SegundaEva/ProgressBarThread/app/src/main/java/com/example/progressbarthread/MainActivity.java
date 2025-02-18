package com.example.progressbarthread;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumber;
    TextView textView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los elementos de la interfaz
        editTextNumber = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textView);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setMax(100); // Definimos el máximo al 100% de progreso

    }

    /* public void clickFactorial(View view) { @@@@@@@@@@@@controlando excepciones
         // Obtenemos el texto del EditText y eliminamos espacios en blanco
         String input = editTextNumber.getText().toString().trim();

         // Validamos si el campo está vacío
         if (input.isEmpty()) {
             Toast.makeText(this, "Debe introducir un número", Toast.LENGTH_SHORT).show();
             return;
         }

         try {
             // Convertimos la cadena a entero
             int numero = Integer.parseInt(input);

             // Creamos una tarea asincrónica para calcular el factorial
             MiTareaAsincronaDialog tarea = new MiTareaAsincronaDialog();
             tarea.execute(numero);
         } catch (NumberFormatException e) {
             // En caso de que no se pueda convertir a número, mostramos un mensaje de error
             Toast.makeText(this, "Ingrese un número válido", Toast.LENGTH_SHORT).show();
         }
     }*/
    public void clickFactorial(View view) {
        // Obtenemos el número del EditText
        int numero = Integer.parseInt(editTextNumber.getText().toString());
        // Creamos una tarea asincrónica para calcular el factorial
        MiTareaAsincronaDialog tarea = new MiTareaAsincronaDialog();
        tarea.execute(numero);
    }


    // Tarea asincrónica para calcular el factorial y actualizar la barra de progreso
    class MiTareaAsincronaDialog extends AsyncTask<Integer, Integer, Integer> { // valor que se recibe execute, valor que se pasa a publishProgress, valor que se devuelve doInBackground a onPostExecute
        ProgressDialog progreso;

        @Override
        protected void onPreExecute() { // metodo que se ejecuta antes de empezar la tarea
           /* progreso = new ProgressDialog(MainActivity.this);
            progreso.setProgress(ProgressDialog.STYLE_HORIZONTAL);
            progreso.setCancelable(true);
            progreso.setMessage("Calculando");
            progreso.setProgress(0);
            progreso.show();*/
            progressBar.setProgress(0);
        }
        @Override
        protected Integer doInBackground(Integer... integers) { // integers es un array de enteros, es el primer parámetro
            int resultado = 1;
            int totalIteraciones = integers[0]; // Número total de iteraciones
            for (int i = 1; i <= totalIteraciones; i++) {
                resultado *= i;

                // Calcular el porcentaje de progreso
                int progreso = (int) (((double) i / totalIteraciones) * 100);

                // Añadimos el sleep en cada iteración para simular un cálculo más lento
                try {
                    Thread.sleep(1000); // Puedes ajustar este valor según lo necesites
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //SystemClock.sleep(1000);

                // Publicamos el progreso calculado en la barra
                publishProgress(progreso); // envia el primer parametro a onProgressUpdate
            }
            return resultado;
        }

        @Override
        protected void onProgressUpdate(Integer... values) { // se usa para actualizar la UI, con el primer parametro de publish progres
            //progreso.setProgress(values[0]); progresDialog
            progressBar.setProgress(values[0]); // actualiza la barra de progreso con el parametro recibido de doInBackground
        }

        @Override
        protected void onPostExecute(Integer integer) { // metodo que ejecuta despues de la tarea en 2o plano, recibe el valor devuelto por doInBackground
            // progreso.dismiss(); progresDIalog, lo hace desaparecer
            progressBar.setProgress(100);
            textView.setText(integer.toString());
        }

    }

}

