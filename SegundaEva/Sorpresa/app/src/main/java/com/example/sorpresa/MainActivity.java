package com.example.sorpresa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listViewEstaciones;
    ArrayList<Estacion> datos = new ArrayList<>();
    BDHelper usdbh;
    SQLiteDatabase dbr;
    AdaptadorEstaciones adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        // Buscar Views
        usdbh= new BDHelper(this, "bdestacionesesqui", null, 21);
        listViewEstaciones = findViewById(R.id.listViewEstaciones);

        Estacion e1 = new Estacion("prubea","cordillera prueba", 12, 12.3f, 123456, 10, "notas prueba");
        datos.add(e1);

        listViewEstaciones.setAdapter(new AdaptadorEstaciones(this, datos));
    }

    /// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ LANZAMOS EL MENU DE BARRA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.main, menu); //

        return true;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ deifinimos las funciones del menu de la barra@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu

        /*if (item.getItemId() == R.id.altaEstacion) {
            Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
            lanzador.launch(i); // Lanzamos el intent que acabos de crear
        }*/
        return true;
    }

    public void inciarListConBD() {

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
                contendor.nota = item.findViewById(R.id.nota);
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
            contendor.imagen.setImageResource(R.drawable.ic_launcher_background); // Asignamos la imagen

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
    public void Consultar(View v) {
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
                nuevaEstacion = new Estacion(nombre, cordillera, n_remontes, km_pistas, fecha_ult_visita, valoracion, notas);

                // Añadir la nueva estación al ArrayList
                datos.add(nuevaEstacion);

            } while (c.moveToNext()); // Avanzamos al siguiente registro
        }

        c.close(); // Cerramos el cursor

        // Ahora que tenemos los datos en el ArrayList, configuramos el adaptador
        AdaptadorEstaciones adaptador = new AdaptadorEstaciones(this, datos);
        listViewEstaciones.setAdapter(adaptador); // Establecemos el adaptador en el ListView
    }


}