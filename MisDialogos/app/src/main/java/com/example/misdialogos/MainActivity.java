package com.example.misdialogos;
// Fragment:
// 1. crear interfaz
// 2.Inicializarlo
// 3. Activar el Listener correspondiente
// Main:
// 4. Crear el fragmento y mostrarlo
// 5. Implementar el interfaz
// 6. Difinir o sobrescribir el procedimiento

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements DialogoSeleccion.IdiomaLista, FragmentoPersonalizado.DatoEdad, FragmentoPersonalizado.DatoNombre, FragmentoFecha.Fecha, FragmentoHora.Hora {
    AlertDialog.Builder ventana;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inicializa el TextView aquí
        tv = findViewById(R.id.tv);
    }

    public void clickFragmento(View v) {
        Fragmento2Botones f2b = new Fragmento2Botones();

        FragmentManager fm = getSupportFragmentManager();

        f2b.show(getSupportFragmentManager(), "xxx");
    }

    public void clickSeleccion(View v) {
        DialogoSeleccion ds = new DialogoSeleccion();
        ds.show(getSupportFragmentManager(), "yyy");
    }


    @Override
    public void idiomaSeleccionado(String idioma, int posicion) {
        tv.setText(idioma + " posición:" + (posicion + 1));
    }

    public void clickPersonalizado(View v) {
        FragmentoPersonalizado fp = new FragmentoPersonalizado();
        fp.show(getSupportFragmentManager(), "zzz");
    }

    public void clicKFecha(View view) {
        FragmentoFecha ff = new FragmentoFecha();
        ff.show(getSupportFragmentManager(), "fecha");
    }

    public void clickHora(View view) {
        FragmentoHora fh = new FragmentoHora();
        fh.show(getSupportFragmentManager(), "hora");
    }

    public void pasarHora(int hora, int min) {//metodo de la interfaz
        tv.setText(hora + ":" + min);
    }

    @Override
    public void pasarDatosNombre(String nombre) { //metodo de la interfaz
        tv.setText("Nombre: " + nombre);
    }

    @Override
    public void pasarDatosEdad(int edad) {//metodo de la interfaz
        tv.setText(" Edad: " + edad);
    }

    @Override
    public void pasarFecha(int año, int mes, int dia) {//metodo de la interfaz
        tv.setText(dia + "/" + (mes + 1) + "/" + año);
    }


    public void clickDialogoMensaje(View v) {
        ventana = new AlertDialog.Builder(this);
        ventana.setTitle("DIALOGO SIMPLE");
        ventana.setMessage("Mensaje de dialogo simple");

        // Creando el primer diálogo
        ventana.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv.setText("Dialogo simple aceptado");
            }
        });
        ventana.show(); // Muestra el diálogo simple

        // Creando el segundo diálogo con dos botones
        AlertDialog.Builder dialogoDosBotones = new AlertDialog.Builder(this);
        dialogoDosBotones.setTitle("Dialogo dos botones");
        dialogoDosBotones.setMessage("Este es un mensaje que usa 2 botones");

        dialogoDosBotones.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv.setText("Botón sí pulsado");
            }
        });
        dialogoDosBotones.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tv.setText("Botón no pulsado");
            }
        });

        dialogoDosBotones.show(); // Muestra el diálogo con dos botones
    }


}