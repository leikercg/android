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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements DialogoSeleccion.IdiomaLista, FragmentoPersonalizado.DatoEdad, FragmentoPersonalizado.DatoNombre, FragmentoFecha.Fecha, FragmentoHora.Hora {


    AlertDialog.Builder ventana;
    TextView tv;
    ActivityResultLauncher<Intent> lanzador = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult resultado) { //es ek resultado del intent
                   Intent i= resultado.getData();
                    tv.setText(i.getStringExtra("NOMBRE"));// Recoger los datos del edit text
                    tv.append(i.getStringExtra("NOMBRELISTA"));
                }
            }
    ); // para abrir activitys y volver



    ////Mostrar el menú
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main_menu, menu);

        return true;

        //getMenuInflater().inflate(R.menu.activity_main, menu);
        //return true; esto es del PDF
    }

    // Selección
    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu

        if (item.getItemId() == R.id.menu_hora) {
            FragmentoHora fh= new FragmentoHora();
            fh.show(getSupportFragmentManager(),"hora");

        } else if (item.getItemId() == R.id.menu_fecha) {
            FragmentoFecha ff= new FragmentoFecha();
            ff.show(getSupportFragmentManager(),"fecha");

        }
        return true;
    }
    ///////

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

      // FragmentManager fm = getSupportFragmentManager(); no hace falta ya que se llamaabajo en support

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
<<<<<<< HEAD

    public void pasarHora(int hora, int min) {//metodo de la interfaz
        tv.setText(hora + ":" + min);
=======
    public void pasarHora(int hora, int min) {//metodo de la interfaz, recibe los datos y realiza algo con ellos
        tv.setText(hora+":"+min);
>>>>>>> 8e3c6e118373b4d2f3cc7e57f8a6a72576c277d0
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

    // Abrir actividad secundaria con el boton de seleccionar nombre
    public void clickNombre(View view){
        Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
        // startActivity(i); // Iniciar la actividad secundaria
        //Meotodo antiguo //startActivityForResult(i, 1);// Volver a la actividad anterior, se pasa el intento y un número de identificacion
        lanzador.launch(i); // Lanzamos el intent que acabos de crear
    }

    /* metoodo antiguo
     @Override
     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // Esto se ejecutar al volver a la actividad, data es un intent
        super.onActivityResult(requestCode, resultCode, data);
        tv.setText(data.getStringExtra("NOMBRE"));// Recoger los datos del edit text
        tv.append(data.getStringExtra("NOMBRELISTA"));
    }*/

}
