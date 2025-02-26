package com.example.sorpresa;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class SecondaryActivity extends AppCompatActivity implements FragmentoFecha.Fecha {

    Button button;
    EditText editTextID, editTextNombre, editTextCordillera, editTextNotas, editTextNumberRemontes, editTextNumberDecimalKM;
    RatingBar ratingBar;
    TextView textViewFecha;


    int id = -1;
    String nombre, cordilleras, notas;
    float kilometros, puntuacion;
    long fecha;
    int remontes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secondary);

        // Buscar Views
        button = findViewById(R.id.button);
        textViewFecha=findViewById(R.id.textViewFecha);
        editTextID = findViewById(R.id.editTextID);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextCordillera = findViewById(R.id.editTextCordillera);
        editTextNotas = findViewById(R.id.editTextNotas);
        editTextNumberRemontes = findViewById(R.id.editTextNumberRemontes);
        editTextNumberDecimalKM = findViewById(R.id.editTextNumberDecimalKM);
        ratingBar = findViewById(R.id.ratingBar);

        Intent i = getIntent(); // recogemos datos pasados desde la main
        if (i.getIntExtra("ID", 0) != -1) { //si es menos uno no se ha enviado una estación, es decir estaddando
            // Toast.makeText(this, "Activado editar", Toast.LENGTH_SHORT).show(); DEBUG

            editTextID.setText(String.valueOf(i.getIntExtra("ID", 0)));
            id = i.getIntExtra("ID", 0); // seteamos la idea a editar
            editTextNombre.setText(i.getStringExtra("NOMBRE"));
            editTextCordillera.setText(i.getStringExtra("CORDILLERAS"));
            editTextNotas.setText(i.getStringExtra("NOTAS"));
            editTextNumberRemontes.setText(String.valueOf(i.getIntExtra("REMONTES", 0)));
            editTextNumberDecimalKM.setText(String.valueOf(i.getFloatExtra("KM", 0.0f)));
            ratingBar.setRating(i.getFloatExtra("VALORACION", 0.0f));
            long fechaMilis = getIntent().getLongExtra("FECHA", 0);

            if (fechaMilis != 0) {
                // Crea un objeto Date con los milisegundos
                Date fecha = new Date(fechaMilis);

                // Usa el Calendar para obtener el día, mes, año, etc.
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);

                // Extrae los valores de día, mes, año, etc.
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                int mes = calendar.get(Calendar.MONTH) + 1; // Los meses comienzan desde 0 (enero)
                int año = calendar.get(Calendar.YEAR);

                textViewFecha.setText(dia + "/" + (mes + 1) + "/" + año);
            } else {
                textViewFecha.setText("Fecha no válida");
            }
        }

    }

    public void aceptar(View view) {

        if (editTextNombre.getText().toString().isEmpty() ||
                editTextCordillera.getText().toString().isEmpty() ||
                editTextNotas.getText().toString().isEmpty() ||
                editTextNumberRemontes.getText().toString().isEmpty() ||
                editTextNumberDecimalKM.getText().toString().isEmpty()) {

            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            //return; // No sigue ejecutando el código
        } else {

            // Si todos los campos están llenos, continua con la lógica
            nombre = editTextNombre.getText().toString();
            cordilleras = editTextCordillera.getText().toString();
            notas = editTextNotas.getText().toString();
            puntuacion = ratingBar.getRating();
            String fechaString = textViewFecha.getText().toString();
            kilometros = Float.parseFloat(editTextNumberDecimalKM.getText().toString());
            remontes = Integer.parseInt(editTextNumberRemontes.getText().toString());

            // Toast.makeText(this, fecha+"", Toast.LENGTH_SHORT).show(); debug


            Intent i = new Intent();
            i.putExtra("NOMBRE", nombre);
            i.putExtra("VALORACION", puntuacion);
            i.putExtra("ID", id);
            i.putExtra("KM", kilometros);
            i.putExtra("REMONTES", remontes);
            i.putExtra("NOTAS", notas);
            i.putExtra("CORDILLERAS", cordilleras);

            // convertir fecha string a milisegundos
            // Usamos Calendar para parsear la fecha
            String[] fechaArray = fechaString.split("/");

            int dia = Integer.parseInt(fechaArray[0]);
            int mes = Integer.parseInt(fechaArray[1]) - 1; // por que van hasta el 11
            int anio = Integer.parseInt(fechaArray[2]);

            // crear un calendario para establecer la fecha
            Calendar calendar = Calendar.getInstance();
            calendar.set(anio, mes, dia);

            // Obtener los milisegundos
            long milisegundos = calendar.getTimeInMillis();


            i.putExtra("FECHA", milisegundos);

            // Alerta de confirmación
            new AlertDialog.Builder(this)
                    .setTitle("¿Estás seguro?")
                    .setMessage("¿Deseas continuar o cancelar?")
                    .setPositiveButton("Continuar", (dialog, which) -> {
                        // Acción a realizar si el usuario presiona "Continuar"
                        Toast.makeText(this, "Acción continuada", Toast.LENGTH_SHORT).show();

                        // Después de la confirmación, se procede con el código
                        setResult(RESULT_OK, i);
                        finish(); // Finaliza la actividad
                    })
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        // Acción a realizar si el usuario presiona "Cancelar"
                        Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show();
                    })
                    .show(); // Mostrar el diálogo
        }
    }

    @Override
    public void pasarFecha(int año, int mes, int dia) {
        textViewFecha.setText(dia+"/"+(mes+1)+"/"+año);
    }
    public void clickElegirFecha(View view) {
        FragmentoFecha ff= new FragmentoFecha();
        ff.show(getSupportFragmentManager(),"fecha");
    }
}
