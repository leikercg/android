package com.example.biblioteca;

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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    DataBaseHelper dataBaseHelper;
    SQLiteDatabase dbreader, dbwriter;
    ArrayList<Libro> datos;
    AdaptadorLibros adaptador;
    ListView listView;
    ActivityResultLauncher<Intent> lanzador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        dataBaseHelper = new DataBaseHelper(this, "biblioteca.db", null, 10);

        dbreader = dataBaseHelper.getReadableDatabase();
        // Insertar datos
        dbwriter = dataBaseHelper.getWritableDatabase();
        String sql2 = "INSERT INTO libros (categoria, titulo, autor, idioma, fecha_lectura_ini, fecha_lectura_fin, prestado_a, valoracion, formato, notas) \n" +
                "VALUES \n" +
                "('Fantasía', 'El Hobbit', 'J.R.R. Tolkien', 'Español', 1672531200000, 1675123200000, NULL, 4.8, 'Físico', 'Un clásico de la fantasía'),\n" +
                "('Historia', 'Cien años de soledad', 'Gabriel García Márquez', 'Español', 1651363200000, 1653955200000, 'Juan Pérez', 2.5, 'Digital', 'Gran obra de la literatura latinoamericana');";
       // dbwriter.execSQL(sql2);


        consultarDatos(false);
        listView.setOnItemClickListener(this); // Asignamos el escuchador de eventos
        registerForContextMenu(listView);


        lanzador = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

                    @Override
                    public void onActivityResult(ActivityResult resultado) {
                        if (resultado != null) {
                            Intent i = resultado.getData();
                            if (i != null && i.getExtras() != null) { // Check if the Intent is not null and contains extras
                                int id = i.getExtras().getInt("ID", 0);
                                String categoria = i.getStringExtra("CATEGORIA");
                                String titulo = i.getStringExtra("TITULO");
                                String autor = i.getStringExtra("AUTOR");
                                String idioma = i.getStringExtra("IDIOMA");
                                long fecha_lectura_ini = i.getLongExtra("FECHA_LECTURA_INI", 0);
                                long fecha_lectura_fin = i.getLongExtra("FECHA_LECTURA_FIN", 0);
                                String prestado_a = i.getStringExtra("PRESTADO_A");
                                float valoracion = i.getFloatExtra("VALORACION", 0);
                                String formato = i.getStringExtra("FORMATO");
                                String notas = i.getStringExtra("NOTAS");

                                if (RESULT_OK == resultado.getResultCode()) {
                                    if (id == 0) {
                                        // Add new book to database
                                        String SQLinsert = "INSERT INTO libros (categoria, titulo, autor, idioma, fecha_lectura_ini, fecha_lectura_fin, prestado_a, valoracion, formato, notas) " +
                                                "VALUES ('" + categoria + "', '" + titulo + "', '" + autor + "', '" + idioma + "', " + fecha_lectura_ini + ", " + fecha_lectura_fin + ", '" + prestado_a +
                                                "', " + valoracion + ", '" + formato + "', '" + notas + "')";
                                        dbwriter.execSQL(SQLinsert);
                                    } else {
                                        // Update existing book in the database
                                        String SQLupdate = "UPDATE libros SET categoria = '" + categoria +
                                                "', titulo = '" + titulo +
                                                "', autor = '" + autor +
                                                "', idioma = '" + idioma +
                                                "', fecha_lectura_ini = " + fecha_lectura_ini +
                                                ", fecha_lectura_fin = " + fecha_lectura_fin +
                                                ", prestado_a = '" + prestado_a +
                                                "', valoracion = " + valoracion +
                                                ", formato = '" + formato +
                                                "', notas = '" + notas +
                                                "' WHERE _id = " + id;
                                        dbwriter.execSQL(SQLupdate);
                                    }

                                    consultarDatos(false);
                                }
                            } else {
                                // Handle case where the intent data is null
                                Toast.makeText(MainActivity.this, "No data received or invalid data", Toast.LENGTH_SHORT).show();
                            }
                        } else if (RESULT_CANCELED == resultado.getResultCode()) {
                            Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
        );


    }

    private void consultarDatos(Boolean ordenado) {
// Consultar datos
        String sql;
        if (ordenado) {
            sql = "SELECT * FROM libros ORDER BY titulo";
        } else {
            sql = "SELECT * FROM libros";
        }
        datos = new ArrayList<>();
        Cursor cursor = dbreader.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String categoria = cursor.getString(1);
            String titulo = cursor.getString(2);
            String autor = cursor.getString(3);
            String idioma = cursor.getString(4);
            long fecha_lectura_ini = cursor.getLong(5);
            long fecha_lectura_fin = cursor.getLong(6);
            String prestado_a = cursor.getString(7);
            float valoracion = cursor.getFloat(8);
            String formato = cursor.getString(9);
            String notas = cursor.getString(10);

            Libro nuevoLibro = new Libro(id, categoria, titulo, autor, idioma, fecha_lectura_ini, fecha_lectura_fin, prestado_a, valoracion, formato, notas);
            datos.add(nuevoLibro);
        }
        cursor.close();
        adaptador = new AdaptadorLibros(this, datos);
        listView.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // metodo de la interfaz AdapterView.OnItemClickListener para mayor claridad
        //Se recibe el adaptador, la vista y el elemento que se ha clicado

        Libro libroSeleccionado = ((Libro) parent.getItemAtPosition(position)); // Hay que hacer un casto
        // Datos de la estación seleccionada
        int idLibro = libroSeleccionado.getId();
        String titulo = libroSeleccionado.getTitulo();
        String autor = libroSeleccionado.getAutor();
        String idioma = libroSeleccionado.getIdioma();
        long fecha_lectura_ini = libroSeleccionado.getFecha_lectura_ini();
        long fecha_lectura_fin = libroSeleccionado.getFecha_lectura_fin();
        String prestado_a = libroSeleccionado.getPrestado_a();
        String formato = libroSeleccionado.getFormato();
        String categoria = libroSeleccionado.getCategoria();
        float valoracion = libroSeleccionado.getValoracion();
        String notas = libroSeleccionado.getNotas();

        Intent i = new Intent(MainActivity.this, SecondaryActivity.class);

        i.putExtra("VALORACION", valoracion);
        i.putExtra("NOTAS", notas);
        i.putExtra("ID", idLibro);
        i.putExtra("CATEGORIA", categoria);
        i.putExtra("TITULO", titulo);
        i.putExtra("AUTOR", autor);
        i.putExtra("IDIOMA", idioma);
        i.putExtra("FECHA_LECTURA_INI", fecha_lectura_ini);
        i.putExtra("FECHA_LECTURA_FIN", fecha_lectura_fin);
        i.putExtra("PRESTADO_A", prestado_a);
        i.putExtra("FORMATO", formato);
        i.putExtra("VALORACION", valoracion);
        i.putExtra("NOTAS", notas);

        lanzador.launch(i); // Lanzamos el intent que acabos de crear

        Toast.makeText(this, "En iten selcionado", Toast.LENGTH_SHORT).show();

    }


    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ CLASE INTERNA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    class AdaptadorLibros extends ArrayAdapter<Libro> {

        public AdaptadorLibros(Context context, ArrayList<Libro> datos) {
            super(context, R.layout.layout_item, datos);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder contendor; // declaro el contador
            View item = convertView;

            if (item == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                item = inflater.inflate(R.layout.layout_item, null); // Inflar el layout

                contendor = new ViewHolder();
                contendor.titulo = item.findViewById(R.id.textViewTitulo);
                contendor.autor = item.findViewById(R.id.textViewAutor);
                contendor.idioma = item.findViewById(R.id.imageView); // Asegúrate de que la referencia esté aquí
                contendor.valoracion = item.findViewById(R.id.ratingBar);
                contendor.fecha_lectura_ini = item.findViewById(R.id.textViewFecha);
                contendor.formato = item.findViewById(R.id.textViewFormato);
                contendor.notas = item.findViewById(R.id.checkBoxNotas);
                contendor.fecha_lectura_fin = item.findViewById(R.id.checkBoxFinalizada);
                contendor.prestado_a = item.findViewById(R.id.checkBoxPrestado);

                item.setTag(contendor);
            } else {
                contendor = (ViewHolder) item.getTag();
            }

            // Definimos cada item
            Libro libro = datos.get(position);
            contendor.titulo.setText(libro.getTitulo());
            contendor.autor.setText(libro.getAutor());
            contendor.valoracion.setRating(libro.getValoracion());
            contendor.idioma.setImageResource(R.drawable.icon);
            contendor.formato.setText("");
            contendor.formato.append("Formato: " + libro.getFormato());


            // Obtener la fecha de lectura inicial en milisegundos y convertir a fecha
            //Date date = new Date(libro.getFecha_lectura_fin());
            //contendor.fecha_lectura_fin.setText(String.valueOf(date));

            Date date = new Date(libro.getFecha_lectura_ini());
            // Crear un objeto SimpleDateFormat con el formato deseado (DD-MM-YYYY)
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            // Formatear la fecha
            String fechaFormateada = sdf.format(date);
            contendor.fecha_lectura_ini.setText(fechaFormateada);


            // Chequear notas
            if (libro.getNotas() != null && !libro.getNotas().isEmpty()) {
                contendor.notas.setChecked(true);
            }

            // Chequear prestado a
            if (libro.getPrestado_a() != null && !libro.getPrestado_a().isEmpty()) {
                contendor.prestado_a.setChecked(true);
            }


            // Chequear finalizado
            if (libro.getFecha_lectura_fin() != 0) {
                contendor.fecha_lectura_fin.setChecked(true);
            }

            // Imagen según idioma
            if (libro.getIdioma().equals("Español")) {
                contendor.idioma.setImageResource(R.drawable.libroesp);
            } else if (libro.getIdioma().equals("Inglés")) {
                contendor.idioma.setImageResource(R.drawable.libroeng);
            }


            return item;
        }
    }

    /*@@@@@@@@@@@@@@@@@@@@ DEFINIMOS EL CONTENEDOR @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
    static class ViewHolder {
        // declaro los elementos del contenedor para poder modificarlos en el adaptador
        TextView categoria;
        TextView titulo;
        TextView autor;
        ImageView idioma;
        TextView fecha_lectura_ini;
        CheckBox prestado_a;
        RatingBar valoracion;
        TextView formato;
        CheckBox notas;
        CheckBox fecha_lectura_fin;
    }


    /// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ LANZAMOS EL MENU DE BARRA @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu); //
        return true;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ deifinimos las funciones del menu de la barra@@@@@@@@@@@@@@@@@@@@@@@@@@@
    public boolean onOptionsItemSelected(MenuItem item) { // Dar funcionalidad a los items del menu
        if (item.getItemId() == R.id.menuItemAgregar) {
            Intent i = new Intent(MainActivity.this, SecondaryActivity.class);
            lanzador.launch(i); // Lanzamos el intent que acabos de crear
        } else if (item.getItemId() == R.id.menuItemOrdenar) {
            consultarDatos(true);
        }
        return true;
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflador = getMenuInflater();

        //AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //menu.setHeaderTitle((AdaptadorTitulares) listaOpciones.getAdapter().getItem(informacionItem.position).toString());
        inflador.inflate(R.menu.contextual_menu, menu);


        //en caso de dos menus //
        /*if (v.getId() == R.id.listView1) {
        // Si el ListView1 disparó el evento, inflamos el primer menú contextual
        getMenuInflater().inflate(R.menu.menu_contextual1, menu);
        } else if (v.getId() == R.id.listView2) {
        // Si el ListView2 disparó el evento, inflamos el segundo menú contextual
        getMenuInflater().inflate(R.menu.menu_contextual2, menu);
         }
        * */
    }

    //////////////////////////////// INDICAMOS QUE HACER EN CASO DE QUE SE SELECCIONE CADA ELEMENTO
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = informacionItem.position; // obtenemos la posicion del elemento
        Libro selectedLibro = datos.get(position); // Obtenemos el alumno seleccionado
        int id = selectedLibro.getId();

        if (item.getItemId() == R.id.contextual_menu_eliminar) {
           datos.remove(position); // Eliminar el objeto de la lista en la posición seleccionada

            String sql = "Delete FROM libros WHERE _id=" + id;

            // para acceder a la base de datos
            dbwriter.execSQL(sql); // Ejecuta sentencia sql sin más.
            //Toast.makeText(this, selectedLibro.getTitulo() + " borrrado", Toast.LENGTH_SHORT).show();
            consultarDatos(false);


        }

        // return super.onContextItemSelected(item);// devuelve el item seleccionado del menu
        return true;
    }


}