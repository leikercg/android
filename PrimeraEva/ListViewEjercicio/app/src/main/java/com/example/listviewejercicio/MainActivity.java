package com.example.listviewejercicio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
//1. Crear ListView, en layout, darle el formato deseado
//2. Crear los datos, un array o un arreglo
//3. Crar adaptador y asjuntarlo, crear adaptador personalizado y adjuntarlo, crear
// viewHolder, que será el contenedor y usarlo




/*MENU CONTEXTUAL:
-Definir la estructura del menu ...XML
-COnverir el menú en lista ... inflate
-Asociar el menú a la vista o cualquier cosa
-Definir listener.
* */

public class MainActivity extends AppCompatActivity {
    ListView listaOpciones;
    ArrayList<Alumno> datos = new ArrayList<>();
    Alumno alumno1 = new Alumno("Leiker", 5);
    Alumno alumno2 = new Alumno("David", 8);

    AdaptadorAlumnos adaptador;// Creamos un adaptador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        datos.add(alumno1);
        datos.add(alumno2);

        listaOpciones = findViewById(R.id.listView);
        adaptador = new AdaptadorAlumnos(this, datos);// Creamos un adaptador

        listaOpciones.setAdapter(adaptador); // Le adjudicamos el adaptador
        registerForContextMenu(listaOpciones); //asociamos la lista al menu
    }


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CLASE INTERNA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    class AdaptadorAlumnos extends ArrayAdapter<Alumno> {

        public AdaptadorAlumnos(Context context, ArrayList<Alumno> datos) {
            super(context, R.layout.layout_item, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder contendor; // declaro el contador
            View item = convertView;

            if (item == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                item = inflater.inflate(R.layout.layout_item, null); // Inflar el layout

                contendor = new ViewHolder();
                contendor.nombre = item.findViewById(R.id.nombre);
                contendor.nota = item.findViewById(R.id.nota);
                contendor.imagen = item.findViewById(R.id.imagen); // Asegúrate de que la referencia esté aquí
                item.setTag(contendor);
            } else {
                contendor = (ViewHolder) item.getTag();
            }

            // Definimos cada item
            Alumno alumno = datos.get(position);
            contendor.nombre.setText(alumno.getNombre());
            contendor.nota.setText(String.valueOf(alumno.getNota())); // Solo acapta strings

            // Cambiamos la imagen dependiendo de la nota
            if (alumno.getNota() < 5) {
                contendor.imagen.setImageResource(R.drawable.suspendido);
            } else {
                contendor.imagen.setImageResource(R.drawable.aprobado);
            }
            return item;
        }
    }

    static class ViewHolder {
        // declaro los elementos del contenedor para poder modificarlos en el adaptador
        TextView nombre;
        TextView nota;
        ImageView imagen;

    }



    /// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ LANZAMOS EL MENU DE BARRA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_bar, menu); //

        return true;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ deifinimos las funciones del menu de la barra@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu

        if (item.getItemId() == R.id.itemAgregar) {
            Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
            lanzador.launch(i); // Lanzamos el intent que acabos de crear
        } else if (item.getItemId() == R.id.itemSalir) {

            finish();
            // Cierra todas las actividades y finaliza la aplicación
            finishAffinity();
            // Termina el proceso de la aplicación, recomendado
            System.exit(0);

        }
        return true;
    }

    /// @@@@@@@@@@@@@@@@@@@@@@@@@ lanzamos la actividad secundaria @@@@@@@@@@@@@@@
    ActivityResultLauncher<Intent> lanzador = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult resultado) { //es ek resultado del intent
                    Intent i = resultado.getData();
                    if (i != null) {
                        String nombre = i.getStringExtra("NOMBRE");
                        float nota = i.getFloatExtra("NOTA", 0.0f);
                        int position = i.getIntExtra("POSITION", -1);

                        if (position != -1) {
                            // El alumno está siendo editado
                            Alumno editedoAlumno = new Alumno(nombre, nota);
                            datos.set(position, editedoAlumno); // Reemplaza el alumno en la posición indicada
                            Toast.makeText(MainActivity.this, "Alumno editado", Toast.LENGTH_SHORT).show();
                        } else {
                            // Se está creando un nuevo alumno
                            Alumno newAlumno = new Alumno(nombre, nota);
                            datos.add(newAlumno); // Añade el nuevo alumno
                            Toast.makeText(MainActivity.this, "Alumno agregado", Toast.LENGTH_SHORT).show();
                        }

                        // Notificar al adaptador que los datos han cambiado y se debe actualizar el ListView
                        adaptador.notifyDataSetChanged();
                    }
                }
            }
    );

    /////////////////// ENLAZAMOS EL MENU CONTEXTUAL CON EL LISTVIEW/////////
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflador = getMenuInflater();

        //AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //menu.setHeaderTitle((AdaptadorTitulares) listaOpciones.getAdapter().getItem(informacionItem.position).toString());
        inflador.inflate(R.menu.menu_contextual, menu);
    }

    //////////////////////////////// INDICAMOS QUE HACER EN CASO DE QUE SE SELECCIONE CADA ELEMENTO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = informacionItem.position; // obtenemos la posicion del elemento
        Alumno selectedAlumno = datos.get(position); // Obtenemos el alumno seleccionado

        if (item.getItemId() == R.id.itemEditar) {
            Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
            // Pasar los datos del alumno seleccionado a la SecondaryActivity
            i.putExtra("POSITION", position); // enviamos la poscion para validar si existe al volver
            i.putExtra("NOMBRE", selectedAlumno.getNombre());
            i.putExtra("NOTA", selectedAlumno.getNota());
            lanzador.launch(i); // Lanzamos el intent que acabos de crear


        } else if (item.getItemId() == R.id.itemEliminar) {
            datos.remove(position); // Eliminar el objeto de la lista en la posición seleccionada

            // Notificar al adaptador que los datos han cambiado y se debe actualizar el ListView
            adaptador.notifyDataSetChanged(); // Esto actualizará la vista
            Toast.makeText(this, "Alumno eliminado", Toast.LENGTH_SHORT).show();
        }

        // return super.onContextItemSelected(item);// devuelve el item seleccionado del menu
        return true;
    }


}