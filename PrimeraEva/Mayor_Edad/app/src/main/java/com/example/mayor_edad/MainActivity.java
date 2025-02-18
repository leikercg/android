package com.example.mayor_edad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText nombre, edad;
    Button finalizar, aceptar;
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<Integer> edades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
    }

    public void aceptar(View v) {
        nombre = findViewById(R.id.nombre);
        edad = findViewById(R.id.edad);

        // Validar que ambos campos no estén vacíos
        if (!edad.getText().toString().isEmpty() && !nombre.getText().toString().isEmpty()) {
            nombres.add(nombre.getText().toString());
            edades.add(Integer.valueOf(edad.getText().toString()));

            // Mostrar un mensaje de confirmación
            Toast.makeText(this, "Datos añadidos: " + nombre.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Por favor, complete ambos campos", Toast.LENGTH_SHORT).show();
        }
        nombre.setText("");
        edad.setText("");

    }

    public void finalizar(View v) {
        int indice = mayorEdad();

        if (indice != -1) {  // Verificar si se encontró un índice válido
            String nombreEnviado = nombres.get(indice);
            int edadEnviada = edades.get(indice);

            Intent intento = new Intent(MainActivity.this, ActividadSecundaria.class);
            intento.putExtra("NOMBRE", nombreEnviado);
            intento.putExtra("EDAD", edadEnviada);

            startActivity(intento);
        } else {
            Toast.makeText(this, "Array vacio", Toast.LENGTH_SHORT).show();
        }
    }

    // Modificado para retornar el índice de la mayor edad
    public int mayorEdad() {
        if (edades.isEmpty()) {  // Verificamos si la lista está vacía
            Toast.makeText(this, "No hay edades registradas", Toast.LENGTH_SHORT).show();
            return -1;  // Retornar -1 si no hay elementos
        }

        int indiceMayor = 0;  // Inicializamos con el primer índice

        for (int i = 1; i < edades.size(); i++) {  // Empezamos desde el segundo elemento
            if (edades.get(i) > edades.get(indiceMayor)) {
                indiceMayor = i;  // Actualizamos el índice si encontramos una edad mayor
            }
        }

        return indiceMayor;  // Retornamos el índice de la persona con mayor edad
    }
}
