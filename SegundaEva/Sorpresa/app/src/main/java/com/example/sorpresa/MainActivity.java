package com.example.sorpresa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listViewEstaciones;
    ArrayList<Estacion> datos = new ArrayList<>();
    BDHelper usdbh;
    SQLiteDatabase dbw, dbr; // Variables para escritura y lectura
    AdaptadorEstaciones adaptador;
    ActivityResultLauncher<Intent> lanzador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Buscar Views
        usdbh = new BDHelper(this, "bdestacionesesqui", null, 31); // Solo creamos una vez la base de datos, su confi
        listViewEstaciones = findViewById(R.id.listViewEstaciones);

        Consultar(); // hay que acceder a la base de datos o intentarlo para que se ejecuten losmetodo de DBhelper

        //Estacion e1 = new Estacion(10, "prubea", "cordillera prueba", 12, 12.3f, 123456, 10, "notas prueba"); DEBUG
        //datos.add(e1); DEBUG


        registerForContextMenu(listViewEstaciones); // registramos el menu contextual en el listview
        listViewEstaciones.setOnItemClickListener(this); // registramos el click en el listview

        // que hacer al volver de la actividad, en caso de dos activities hay que definir dos lanzadores
        lanzador = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resultado) { //es ek resultado del intent
                        if (resultado.getResultCode() == RESULT_OK) {
                            Intent i = resultado.getData();
                            if (i.getIntExtra("ID", 0) == -1) { // si se es crear
                                String nombre = i.getStringExtra("NOMBRE");
                                String cordilleras = i.getStringExtra("CORDILLERAS");
                                String notas = i.getStringExtra("NOTAS");
                                int remontes = i.getIntExtra("REMONTES", 0);
                                float kilometros = i.getFloatExtra("KM", 0.0f);
                                long fecha = i.getLongExtra("FECHA", 0);
                                float valoracion = i.getFloatExtra("VALORACION", 0.0f);
                                int id = i.getIntExtra("ID", 0);
                                Toast.makeText(MainActivity.this, fecha+"", Toast.LENGTH_SHORT).show();

                                // Se está creando un nuevo estacion
                                //Estacion nuevaEstacion = new Estacion(id, nombre, cordilleras, remontes, kilometros, fecha, valoracion, notas); //no hace falta, consultamos database
                                // datos.add(nuevaEstacion); // Añade el nuevo alumno, luego hay que actualizarla, no hace falta, consultamos database

                                // Insertar en la base de datos
                                dbw = usdbh.getWritableDatabase(); // para acceder a la base
                                dbw.execSQL("INSERT INTO esqui (nombre, cordillera, n_remontes, km_pistas, fecha_ult_visita, valoracion, notas) VALUES ('" + nombre + "', '" + cordilleras + "', '" + remontes + "', '" + kilometros + "', '" + fecha + "', '" + valoracion + "', '" + notas + "')");

                                Consultar();
                                // Notificar al adaptador que los datos han cambiado y se debe actualizar el ListView

                                Toast.makeText(MainActivity.this, "Estacion agregado", Toast.LENGTH_SHORT).show();

                            } else {
                                String nombre = i.getStringExtra("NOMBRE");
                                String cordilleras = i.getStringExtra("CORDILLERAS");
                                String notas = i.getStringExtra("NOTAS");
                                int remontes = i.getIntExtra("REMONTES", 0);
                                float kilometros = i.getFloatExtra("KM", 0.0f);
                                long fecha = i.getLongExtra("FECHA", 0);
                                float valoracion = i.getFloatExtra("VALORACION", 0.0f);
                                int id = i.getIntExtra("ID", 0);

                                // Se está editando un estacion
                                dbw = usdbh.getWritableDatabase(); // para acceder a la base
                                dbw.execSQL("UPDATE esqui SET nombre='" + nombre + "', cordillera='" + cordilleras + "', n_remontes='" + remontes + "', km_pistas='" + kilometros + "', fecha_ult_visita='" + fecha + "', valoracion='" + valoracion + "', notas='" + notas + "' WHERE _id=" + id);
                                Toast.makeText(MainActivity.this, "Estacion modificado", Toast.LENGTH_SHORT).show();
                                // Notificar al adaptador que los datos han cambiado y se debe actualizar el ListView

                                Consultar();
                            }

                        } else {
                            // no hacemos nada
                        }

                    }
                }
        );

    }

    /// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ LANZAMOS EL MENU DE BARRA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main, menu); //

        return true;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ deifinimos las funciones del menu de la barra@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu
        if (item.getItemId() == R.id.altaEstacion) {
            Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
            i.putExtra("ID", -1);
            lanzador.launch(i); // Lanzamos el intent que acabos de crear
        }
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long id) { // Con interfaz para más claridad
        //Se recibe el adaptador, la vista y el elemento que se ha clicado

        //Alternativa 1: Adaptador y posicion
        // Estacion estacionSeleccionada = datos.get(posicion);

        // otra alternativa:
        Estacion opcionSeleccionada = ((Estacion) adapterView.getItemAtPosition(posicion)); // Hay que hacer un casto
        // Datos de la estación seleccionada
        int idEstacion = opcionSeleccionada.getId();
        String nombreEstacion = opcionSeleccionada.getNombre();
        String cordilleras = opcionSeleccionada.getCordillera();
        int remontes = opcionSeleccionada.getN_remontes();
        float kilometros = opcionSeleccionada.getKm_pistas();
        long fecha = opcionSeleccionada.getFecha_ult_visita();
        float valoracion = opcionSeleccionada.getValoracion();
        String notas = opcionSeleccionada.getNotas();

        Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
        i.putExtra("NOMBRE", nombreEstacion);
        i.putExtra("CORDILLERAS", cordilleras);
        i.putExtra("REMONTES", remontes);
        i.putExtra("KM", kilometros);
        i.putExtra("FECHA", fecha);
        i.putExtra("VALORACION", valoracion);
        i.putExtra("NOTAS", notas);
        i.putExtra("ID", idEstacion);

        lanzador.launch(i); // Lanzamos el intent que acabos de crear

        Toast.makeText(this, "En iten selcionado", Toast.LENGTH_SHORT).show();


    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CLASE INTERNA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    class AdaptadorEstaciones extends ArrayAdapter<Estacion> {

        public AdaptadorEstaciones(Context context, ArrayList<Estacion> datos) {
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
                contendor.cordillera = item.findViewById(R.id.cordillera);
                contendor.n_remontes = item.findViewById(R.id.remontes);
                contendor.km_pistas = item.findViewById(R.id.km);

                contendor.imagen = item.findViewById(R.id.imagen); // Asegúrate de que la referencia esté aquí
                item.setTag(contendor);
            } else {
                contendor = (ViewHolder) item.getTag();
            }

            // Definimos cada item
            Estacion estacion = datos.get(position);
            contendor.nombre.setText(estacion.getNombre());
            contendor.cordillera.setText(estacion.getCordillera());
            contendor.n_remontes.setText(String.valueOf(estacion.getN_remontes()));
            contendor.km_pistas.setText(String.valueOf(estacion.getKm_pistas()));
            contendor.imagen.setImageResource(R.drawable.nochenube); // Asignamos la imagen

            return item;
        }
    }

    static class ViewHolder {
        // declaro los elementos del contenedor para poder modificarlos en el adaptador
        TextView nombre;
        TextView nota;
        TextView cordillera;
        TextView n_remontes;
        TextView km_pistas;
        ImageView imagen;

    }

    public void Consultar() {
        dbr = usdbh.getReadableDatabase(); // Abrimos la base de datos en modo lectura

        // Realizamos una consulta para obtener todos los registros de la tabla "esqui"
        Cursor c = dbr.rawQuery("SELECT * FROM esqui", null);

        Estacion nuevaEstacion;

        // Limpiar la lista antes de agregar nuevos datos
        datos.clear();

        if (c.moveToFirst()) {
            do {
                // Extraemos los valores de cada columna del cursor
                @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("_id"));
                @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
                @SuppressLint("Range") String cordillera = c.getString(c.getColumnIndex("cordillera"));
                @SuppressLint("Range") int n_remontes = c.getInt(c.getColumnIndex("n_remontes"));
                @SuppressLint("Range") float km_pistas = c.getFloat(c.getColumnIndex("km_pistas"));
                @SuppressLint("Range") long fecha_ult_visita = c.getLong(c.getColumnIndex("fecha_ult_visita"));
                @SuppressLint("Range") float valoracion = c.getFloat(c.getColumnIndex("valoracion"));
                @SuppressLint("Range") String notas = c.getString(c.getColumnIndex("notas"));

                // Crear un objeto Estacion con los datos obtenidos
                nuevaEstacion = new Estacion(id, nombre, cordillera, n_remontes, km_pistas, fecha_ult_visita, valoracion, notas);

                // Añadir la nueva estación al ArrayList
                datos.add(nuevaEstacion);

            } while (c.moveToNext()); // Avanzamos al siguiente registro
        }

        c.close(); // Cerramos el cursor

        // Ahora que tenemos los datos en el ArrayList, configuramos el adaptador
        adaptador = new AdaptadorEstaciones(this, datos); // inicializamos el adaptador
        listViewEstaciones.setAdapter(adaptador); // Establecemos el adaptador en el ListView para mostrar los datos
    }

    /////////////////// ENLAZAMOS EL MENU CONTEXTUAL CON EL LISTVIEW/////////
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflador = getMenuInflater();

        //AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //menu.setHeaderTitle((AdaptadorTitulares) listaOpciones.getAdapter().getItem(informacionItem.position).toString());
        inflador.inflate(R.menu.contextual, menu);
    }

    //////////////////////////////// INDICAMOS QUE HACER EN CASO DE QUE SE SELECCIONE CADA ELEMENTO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = informacionItem.position; // obtenemos la posicion del elemento
        Estacion estacionSeleccionada = datos.get(position); // Obtenemos el alumno seleccionado
        int id = estacionSeleccionada.getId(); // Obtenemos el id de la estacion seleccionada

        if (item.getItemId() == R.id.contextualEliminar) {
            datos.remove(position); // Eliminar el objeto de la lista en la posición seleccionada

            // Notificar al adaptador que los datos han cambiado y se debe actualizar el ListView
            adaptador.notifyDataSetChanged(); // Esto actualizará la vista

            String sql = "Delete FROM esqui WHERE _id=" + id;

            dbw = usdbh.getWritableDatabase(); // para acceder a la base de datos
            dbw.execSQL(sql); // Ejecuta sentencia sql sin más.
            Toast.makeText(this, "Estacion eliminada en la bd", Toast.LENGTH_SHORT).show();
        }

        // return super.onContextItemSelected(item);// devuelve el item seleccionado del menu
        return true;
    }

}