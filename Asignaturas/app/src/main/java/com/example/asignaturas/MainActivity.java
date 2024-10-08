package com.example.asignaturas;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

RadioGroup rg;
RadioButton ingles, aleman, frances;
CheckBox bbdd, programacion, marcas, procesos, interfaces;

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

}