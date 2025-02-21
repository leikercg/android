package com.example.bdrecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView vistaRecycler; // RecyclerView para mostrar la lista de elementos
    ArrayList<DatosPersonales> lista = new ArrayList<>(); // Lista que almacena los datos personales
    Adaptador adaptador; // Adaptador para gestionar los elementos en el RecyclerView
    TextView tv; // TextView para mostrar información en la pantalla
    ActivityResultLauncher<Intent> lanzadorAlta; // Lanzador de actividad para agregar o editar datos
    ActivityResultLauncher<Intent> lanzadorEdicion;

    int posicionEdicion; // Para guardar la posición del elemento a editar en el menú contextual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        inicializaLista(); // Inicializa la lista con datos de ejemplo
        vistaRecycler = findViewById(R.id.recyclerView);
        adaptador = new Adaptador(this, lista, this); // Se pasa el contexto, la lista y el listener de clics
        tv = findViewById(R.id.textView);
        vistaRecycler.setLayoutManager(new LinearLayoutManager(this)); // Se usa un LinearLayoutManager para disposición en lista
        vistaRecycler.setAdapter(adaptador);

        // Inicializamos el lanzador de actividad para agregar o editar elementos
        lanzadorAlta = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resultado) { // Procesa el resultado de la actividad
                        if (resultado.getResultCode() == RESULT_OK) {
                            String nombre = resultado.getData().getStringExtra("NOMBRE");
                            int edad = Integer.parseInt(resultado.getData().getStringExtra("EDAD"));
                            if (posicionEdicion == -999) { // Si es un nuevo registro
                                lista.add(new DatosPersonales(nombre, edad)); // Agrega un nuevo elemento
                                adaptador.notifyDataSetChanged(); // Notifica cambios en la lista
                            } else { // Si es una edición de un elemento existente
                                lista.set(posicionEdicion, new DatosPersonales(nombre, edad));
                                adaptador.notifyDataSetChanged();
                            }
                        } else if (resultado.getResultCode() == RESULT_CANCELED) { // Si el usuario cancela
                            tv.setText("Cancelado");
                            if (resultado.getData() != null) {
                                String nombre = resultado.getData().getStringExtra("NOMBRE");
                                Toast.makeText(MainActivity.this, "Operación cancelada: " + nombre, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "No se han recibido datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void inicializaLista() { // Método para inicializar la lista con datos de prueba
        lista.add(new DatosPersonales("Pepito", 23));
        lista.add(new DatosPersonales("Jorgito", 13));
        lista.add(new DatosPersonales("Juanito", 14));
        lista.add(new DatosPersonales("Jaimito", 15));
    }

    @Override
    public void onClick(View view) { // Maneja los clics en los elementos del RecyclerView
        tv.setText(lista.get(vistaRecycler.getChildAdapterPosition(view)).getNombre()); // Muestra el nombre del elemento seleccionado
    }

    ////////// CREAR MENU
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pricipal, menu);
        return true;
    }

    ///// OPCIONES DE MENU
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_alta) { // Si se selecciona la opción de agregar
            tv.setText("ALTA");
            posicionEdicion = -999; // Se indica que es un nuevo elemento
            lanzadorAlta.launch(new Intent(this, AltaActivity.class)); // Inicia la actividad para agregar datos
        }
        return true;
    }

    //QUE HACER EN EL MENU CONTEXTUAL
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) { // Maneja las opciones del menú contextual
        switch (item.getItemId()) {
            case 121: // BORRAR
                tv.setText("BORRAR ");
                lista.remove(item.getGroupId()); // Elimina el elemento en la posición seleccionada
                adaptador.notifyDataSetChanged(); // Actualiza la vista
                return true;
            case 122: // EDITAR
                tv.setText("EDITAR");
                posicionEdicion = item.getGroupId(); // Guarda la posición para editar
                Intent i = new Intent(this, AltaActivity.class);
                i.putExtra("NOMBRE", lista.get(item.getGroupId()).getNombre()); // Pasa el nombre del elemento a editar
                i.putExtra("EDAD", lista.get(item.getGroupId()).getEdad()); // Pasa la edad del elemento a editar
                lanzadorAlta.launch(i); // Lanza la actividad de edición
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
