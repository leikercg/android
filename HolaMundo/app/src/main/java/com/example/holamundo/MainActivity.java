package com.example.holamundo;

import android.content.Intent;
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
    TextView nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.nombre);
    }

    public void onClick(View v) {

        //Toast.makeText(this, nombre.getText().toString(),Toast.LENGTH_LONG).show();
        Toast tostada;
        tostada = Toast.makeText(this, nombre.getText().toString(), Toast.LENGTH_LONG);

        tostada.show();

        Intent intento = new Intent(MainActivity.this, ActividadSecundaria.class);
        intento.putExtra("NOMBRE", nombre.getText().toString());
        startActivity(intento);
    }
}