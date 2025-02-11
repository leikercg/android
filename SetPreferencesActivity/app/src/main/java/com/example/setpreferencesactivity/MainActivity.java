package com.example.setpreferencesactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }
    public void clickGuardar1(View v) {
        Intent i = new Intent(this, Opcionesctivity.class);
        startActivity(i);
    }
    public void clickObtener(View v) {
        SharedPreferences preferencias = PreferenceManager.getDefaultSharedPreferences(this);
        boolean preferencia1 = preferencias.getBoolean("opcion1", false);
        String preferencia2 = preferencias.getString("opcion2", "");
        String preferencia3 = preferencias.getString("opcion3", "");

        Log.i("Preferencias", "Preferencia 1: " + preferencia1);
        Log.i("Preferencias", "Preferencia 2: " + preferencia2);
        Log.i("Preferencias", "Preferencia 3: " + preferencia3);

    }
}