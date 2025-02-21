package com.example.repasofinal;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button buttonCrono, buttonToast;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        buttonCrono = findViewById(R.id.buttonCrono);
        buttonToast = findViewById(R.id.buttonToast);
        textView = findViewById(R.id.textView);

    }

    public void onClickToast(View view) {
        Toast.makeText(this, "Hola, no estoy bloqueado", Toast.LENGTH_SHORT).show();
    }

    public void onClickCrono(View view) {
        TareaAsincrona tarea = new TareaAsincrona();
        tarea.execute();
    }

    class TareaAsincrona extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            int segundos = 0;
            int minutos = 0;
            boolean parar = false;
            while (parar == false) {
                try {
                    Thread.sleep(1000);
                    segundos = segundos + 1;

                    if (segundos == 10) {
                        minutos = minutos + 1;
                        segundos = 0;
                    }
                    publishProgress(minutos, segundos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (values[1] < 10) {
                textView.setText(values[0] + ":0" + values[1]);
            } else {
                textView.setText(values[0] + ":" + values[1]);
            }
        }

    }
}