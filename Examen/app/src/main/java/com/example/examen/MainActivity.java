package com.example.examen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnColores, btnMayor, btnCapicua;




    AlertDialog.Builder ventana;
    ActivityResultLauncher<Intent> lanzador = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult resultado) { //es ek resultado del intent
                    //Que hacer al volver de la actividad lanzada
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //Iniciamos los botones.
        btnColores = findViewById(R.id.buttonColores);
        btnMayor = findViewById(R.id.buttonMayor);
        btnCapicua = findViewById(R.id.buttonCapicua);

    }


    /// Metodos onclick


    public void onclickColores(View view) { //Lanzamos actividad colores

        Intent i = new Intent(MainActivity.this, ColoresActivity.class);
        lanzador.launch(i); // Lanzamos el intent que acabos de crear

    }

    public void onclickMayor(View view) {
        Intent i = new Intent(MainActivity.this, MayorActivity.class);
        lanzador.launch(i); // Lanzamos el intent que acabos de crear

    }

    public void onclickCapicua(View view) {
        Intent i = new Intent(MainActivity.this, CapicuaActivity.class);
        lanzador.launch(i); // Lanzamos el intent que acabos de crear
    }
}