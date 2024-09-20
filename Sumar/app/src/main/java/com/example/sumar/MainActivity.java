package com.example.sumar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button boton;
    EditText n1, n2;
    TextView resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        n1= findViewById(R.id.n1);
        n2= findViewById(R.id.n2);
        resultado=findViewById(R.id.resultado);

    }

    public void sumar(View view){
        int num1=Integer.parseInt(n1.getText().toString());
        int num2=Integer.parseInt(n2.getText().toString());

        int res = num1+num2;

        resultado.setText(res + "");

    }
}