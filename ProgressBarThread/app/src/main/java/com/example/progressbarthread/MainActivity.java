package com.example.progressbarthread;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los elementos de la interfaz
        editTextNumber = findViewById(R.id.editTextNumber);
        textView = findViewById(R.id.textView);

    }

    // Tarea asincrónica para calcular el factorial y actualizar la barra de progreso
    private class MiTareaAsincronaDialog extends AsyncTask<Integer, Integer, Integer> {


    }
}



