package com.example.examen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class MayorActivity extends AppCompatActivity implements FragmentoFecha1.Fecha, FragmentoFecha.Fecha {
    EditText nombre1, nombre2;
    TextView edad1, edad2;
    Button btnMostrarMayor;

    Calendar fecha1 = Calendar.getInstance();
    Calendar fecha2 = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) { // Al crear la vista

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.mayor_activity); // Indicamos la actividad a la que se refiere

        nombre1 = findViewById(R.id.editTextNombre1);
        nombre2 = findViewById(R.id.editTextNombre2);
        edad1 = findViewById(R.id.textViewEdad1);
        edad2 = findViewById(R.id.textViewEdad2);
        btnMostrarMayor = findViewById(R.id.buttonVerMayor);


    }

    @Override
    public void pasarFecha(int año, int mes, int dia) {

        edad1.setText(dia + "/" + mes + "/" + año);

    }

    public void onclickDarFecha(View view) {
        FragmentoFecha ff = new FragmentoFecha();
        ff.show(getSupportFragmentManager(), "xxx");
    }

    public void onclickDarFecha1(View view) {
        FragmentoFecha1 ff = new FragmentoFecha1();
        ff.show(getSupportFragmentManager(), "xxx");
    }


    @Override
    public void pasarFecha1(int año, int mes, int dia) {

        edad2.setText(dia + "/" + mes + "/" + año);

    }

}
