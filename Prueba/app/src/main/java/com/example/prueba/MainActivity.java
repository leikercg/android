package com.example.prueba;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    ToggleButton tb;
    Switch sw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.texto);
        tb=findViewById(R.id.toggleButton);
        sw=findViewById(R.id.switch1);

        /*tb.setOnClickListener(new View.OnClickListener() { // Los listener se asignan el onCreate.
            public void onClick(View v) {
                if (tb.isChecked()) {
                    tv.setText("boton toggle encendido");
                } else {
                    tv.setText("boton toggle apagado");
                }
            }
        });*/
    }
    public void clickBoton(View v){

        if (v.getId()==R.id.btn_normal){
            tv.setText("Boton normal pulsado");
        }
        else if (v.getId()==R.id.imageButton){
            tv.setText("Botón imagen pulsado");
        }else if(v.getId()==R.id.toggleButton){
            if (tb.isChecked()) {
                tv.setText("boton toggle encendido");
            } else {
                tv.setText("boton toggle apagado");
            }
        }else{
            if (sw.isChecked()) {
                tv.setText("boton SWITCHER encendido");
            } else {
                tv.setText("boton SWITCHER apagado");
            }
        }

    }

}