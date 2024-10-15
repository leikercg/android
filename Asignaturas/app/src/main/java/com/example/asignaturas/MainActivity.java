package com.example.asignaturas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

RadioGroup rg;
RadioButton ingles, aleman, frances;
CheckBox bbdd, programacion, marcas, procesos, interfaces;
ArrayList <String> seleccionadas = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Radio buttons
        rg=findViewById(R.id.radiogroup);
        ingles=findViewById(R.id.rb_ingles);
        aleman=findViewById(R.id.rb_aleman);
        frances=findViewById(R.id.rb_fraces);

        // CheckBox;
        bbdd= findViewById(R.id.bbdd);
        procesos= findViewById(R.id.procesos);
        programacion= findViewById(R.id.programacion);
        marcas= findViewById(R.id.marcas);
        interfaces= findViewById(R.id.interfaces);

    }

    public void enviar(View view) {
        // Obtener el RadioButton seleccionado
        RadioButton seleccionado = findViewById(rg.getCheckedRadioButtonId());



        seleccionadas.clear();
        // Verificar si el checkbox de 'bbdd' está marcado y agregarlo al ArrayList
        if (bbdd.isChecked()) {
            seleccionadas.add(bbdd.getText().toString());
        }
        if (procesos.isChecked()) {
            seleccionadas.add(procesos.getText().toString());
        }
        if (programacion.isChecked()) {
            seleccionadas.add(programacion.getText().toString());
        }
        if (marcas.isChecked()) {
            seleccionadas.add(marcas.getText().toString());
        }
        if (interfaces.isChecked()) {
            seleccionadas.add(interfaces.getText().toString());
        }

        // Mostrar el valor del RadioButton seleccionado
        Toast.makeText(this, seleccionado.getText().toString(), Toast.LENGTH_SHORT).show();

        // Mostrar las asignaturas seleccionadas en el ArrayList
        Toast.makeText(this, seleccionadas.toString(), Toast.LENGTH_SHORT).show();

        Intent intento = new Intent(MainActivity.this, ActividadSecundaria.class);
        intento.putExtra("IDIOMA", seleccionado.getText().toString());
        intento.putStringArrayListExtra("ASIGNATURAS", seleccionadas);

        startActivity(intento);
    }



}