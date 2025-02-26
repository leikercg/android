package com.example.biblioteca;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SecondaryActivity extends AppCompatActivity implements FragmentoFecha.Fecha, FragmentoFechaFin.Fecha {

    // Para mostrar datos
    ArrayList<String> categorias, idiomas, formatos;
    EditText editTextTitulo, editTextAutor, editTextPrestado;
    Spinner spinnerCategoria, spinnerIdioma, spinnerFormato;
    RatingBar ratingBarSecondary;
    Button buttonCambiarInicio, buttonCambiarFin;
    TextView textViewFechaInicio, textViewFechaFin;

    // Para enviar datos a bbdd en main
    int id;
    String categoria, titulo, autor, idioma, prestado_a, formato, notas;
    long fecha_lectura_ini, fecha_lectura_fin;
    float valoracion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_secondary);



        // Obtener referencias a los elementos de la interfaz
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextAutor = findViewById(R.id.editTextAutor);
        editTextPrestado = findViewById(R.id.editTextPrestado);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        spinnerIdioma = findViewById(R.id.spinner);
        spinnerFormato = findViewById(R.id.spinnerFormato);
        ratingBarSecondary = findViewById(R.id.ratingBarSecondary);
        buttonCambiarInicio = findViewById(R.id.buttonCambiarInicio);
        buttonCambiarFin = findViewById(R.id.buttonCambiarFin);
        textViewFechaInicio = findViewById(R.id.textViewFechaInicio);
        textViewFechaFin = findViewById(R.id.textViewFechaFin);



        // Inicializar las listas de opciones de categoría
        categorias = new ArrayList<>();
        categorias.add("");
        categorias.add("Ciencia");
        categorias.add("Historia");
        categorias.add("Fantasía");

        // Inicializar las listas de opciones de idioma
        idiomas = new ArrayList<>();
        idiomas.add("");
        idiomas.add("Español");
        idiomas.add("Inglés");
        idiomas.add("Francés");

        // Inicializar las listas de opciones de formato
        formatos = new ArrayList<>();
        formatos.add("");
        formatos.add("Físico");
        formatos.add("Digital");
        formatos.add("Audio");

        spinnerCategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias));
        spinnerIdioma.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, idiomas));
        spinnerFormato.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, formatos));

        Intent i = getIntent();
        // Validamos si el intent contiene datos para rellenar
        if (i.getExtras() == null) { // si no contiene datos la id es 0
            id = 0;
        } else {
            id = getIntent().getExtras().getInt("ID");// si contiene datos la id es la que nos pasan, para editar
            editTextAutor.setText(getIntent().getExtras().getString("AUTOR"));
            editTextTitulo.setText(getIntent().getExtras().getString("TITULO"));
            editTextPrestado.setText(getIntent().getExtras().getString("PRESTADO_A"));
            ratingBarSecondary.setRating(getIntent().getExtras().getFloat("VALORACION"));
            fecha_lectura_fin=getIntent().getExtras().getLong("FECHA_LECTURA_FIN");
            fecha_lectura_ini=getIntent().getExtras().getLong("FECHA_LECTURA_INI");

            // Poner la fecha de fin si no es 0
            if (getIntent().getExtras().getLong("FECHA_LECTURA_FIN") == 0) {
                textViewFechaFin.setText("Fecha de fin: ");
            } else {
                Date date = new Date(i.getExtras().getLong("FECHA_LECTURA_FIN"));
                // Crear un objeto SimpleDateFormat con el formato deseado (DD-MM-YYYY)
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                // Formatear la fecha
                String fechaFormateada = sdf.format(date);
                textViewFechaFin.setText("Fecha de fin: " + fechaFormateada);
            }

            // Poner la fecha de inicio
            Date date = new Date(i.getExtras().getLong("FECHA_LECTURA_INI"));
            // Crear un objeto SimpleDateFormat con el formato deseado (DD-MM-YYYY)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            // Formatear la fecha
            String fechaFormateada = sdf.format(date);
            textViewFechaInicio.setText("Fecha inicio: " + fechaFormateada);


            // Verificar que las listas no sean nulas antes de hacer la selección
            if (categorias != null && categorias.size() > 0) {
                String categoria = getIntent().getExtras().getString("CATEGORIA");
                if (categoria != null) {
                    int categoriaPos = categorias.indexOf(categoria);
                    if (categoriaPos != -1) {
                        spinnerCategoria.setSelection(categoriaPos);
                    } else {
                        spinnerCategoria.setSelection(0); // Valor por defecto
                    }
                }
            }

            // Asegúrate de que las demás listas también se manejen de forma similar
            if (idiomas != null && idiomas.size() > 0) {
                String idioma = getIntent().getExtras().getString("IDIOMA");
                if (idioma != null) {
                    int idiomaPos = idiomas.indexOf(idioma);
                    if (idiomaPos != -1) {
                        spinnerIdioma.setSelection(idiomaPos);
                    } else {
                        spinnerIdioma.setSelection(0); // Valor por defecto
                    }
                }
            }

            if (formatos != null && formatos.size() > 0) {
                String formato = getIntent().getExtras().getString("FORMATO");
                if (formato != null) {
                    int formatoPos = formatos.indexOf(formato);
                    if (formatoPos != -1) {
                        spinnerFormato.setSelection(formatoPos);
                    } else {
                        spinnerFormato.setSelection(0); // Valor por defecto
                    }
                }
            }


        }

        // Configurar los listeners de los spiners
        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    Toast.makeText(getApplicationContext(), categorias.get(i), Toast.LENGTH_SHORT).show();
                    categoria = categorias.get(i);// optenemos la categoria
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SecondaryActivity.this, "nada seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar los listeners de idioma
        spinnerIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    Toast.makeText(getApplicationContext(), idiomas.get(i), Toast.LENGTH_SHORT).show();
                    idioma = idiomas.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SecondaryActivity.this, "nada seleccionado", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar los listeners de idioma
        spinnerFormato.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    Toast.makeText(getApplicationContext(), formatos.get(i), Toast.LENGTH_SHORT).show();
                    // para manejar no selecciona gregar un elemento vacio y no aceptarlo
                    formato = formatos.get(i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SecondaryActivity.this, "nada seleccionado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void clickAceptar(View view) {
        if (editTextTitulo.getText().toString().isEmpty() || editTextAutor.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerCategoria.getSelectedItemPosition() == 0 || spinnerIdioma.getSelectedItemPosition() == 0 || spinnerFormato.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Debe seleccionar una opción de cada lista", Toast.LENGTH_SHORT).show();
            return;
        }

        if (textViewFechaInicio.getText().toString().length() < 18 && textViewFechaFin.getText().toString().length() > 14) {
            Toast.makeText(this, "Debe seleccionar una fecha de iniciasdasdo ", Toast.LENGTH_SHORT).show();
            textViewFechaFin.setText("Fecha de fin: ");
            return;
        }
        if (textViewFechaInicio.getText().toString().length() < 18) {
            Toast.makeText(this, "Debe seleccionar una fecha de inicio", Toast.LENGTH_SHORT).show();
            return;
        }


        titulo = editTextTitulo.getText().toString();
        autor = editTextAutor.getText().toString();
        prestado_a = editTextPrestado.getText().toString();
        valoracion = ratingBarSecondary.getRating();
        notas = "";

        Intent i = new Intent();
        i.putExtra("ID", id);
        i.putExtra("CATEGORIA", categoria);
        i.putExtra("TITULO", titulo);
        i.putExtra("AUTOR", autor);
        i.putExtra("IDIOMA", idioma);
        i.putExtra("FORMATO", formato);
        i.putExtra("FECHA_LECTURA_INI", fecha_lectura_ini);
        i.putExtra("FECHA_LECTURA_FIN", fecha_lectura_fin);
        i.putExtra("VALORACION", valoracion);
        i.putExtra("PRESTADO_A", prestado_a);
        i.putExtra("NOTAS", notas);


        confirmar(i);

    }

    @Override
    public void pasarFecha(int año, int mes, int dia) { // usando calendar para obtener la fecha en milisegundos
        /// Mostrar la fecha seleccionada en el TextView
        textViewFechaInicio.setText("Fecha de inicio: " + dia + "/" + (mes + 1) + "/" + año);

        // Usar Calendar para convertir a milisegundos
        Calendar calendar = Calendar.getInstance();
        calendar.set(año, mes, dia, 0, 0, 0); // Establecer el año, mes y día, con hora 00:00:00

        // Obtener la fecha en milisegundos
        fecha_lectura_ini = calendar.getTimeInMillis();
    }

    @Override
    public void pasarFechaFin(int año, int mes, int dia) {
        /// Mostrar la fecha seleccionada en el TextView
        textViewFechaFin.setText("Fecha de fin: " + dia + "/" + (mes + 1) + "/" + año);
        // Usar Calendar para convertir a milisegundos
        Calendar calendar = Calendar.getInstance();
        calendar.set(año, mes, dia, 0, 0, 0); // Establecer el año, mes y día, con hora 00:00:00

        // Obtener la fecha en milisegundos
        fecha_lectura_fin = calendar.getTimeInMillis();
        Toast.makeText(this, fecha_lectura_fin + "", Toast.LENGTH_SHORT).show();
    }

    public void clickCambiarInicio(View view) {
        FragmentoFecha fragmentoFecha = new FragmentoFecha();
        fragmentoFecha.show(getSupportFragmentManager(), "Fecha");

    }

    public void clickCambiarFin(View view) {
        FragmentoFechaFin fragmentoFecha = new FragmentoFechaFin();
        fragmentoFecha.show(getSupportFragmentManager(), "Fecha");
    }

    public void confirmar(Intent i) {
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