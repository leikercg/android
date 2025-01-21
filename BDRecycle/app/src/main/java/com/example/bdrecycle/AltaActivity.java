package com.example.bdrecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AltaActivity extends AppCompatActivity {
EditText etNombre, etEdad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alta);

        etNombre= findViewById(R.id.editTextNombre);
        etEdad=findViewById(R.id.editTextEdad);
    }
    public void clickVolver(View view){
        Intent i = new Intent();
        i.putExtra("NOMBRE", etNombre.getText().toString());
        i.putExtra("EDAD", etEdad.getText().toString());

        setResult(RESULT_OK,i); // devolvemos esto
        finish();
    }
}