package com.example.biblioteca;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;
    SQLiteDatabase dbreader, dbwriter;
    ArrayList<Libro> datos;
    AdaptadorLibros adaptador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this, "biblioteca.db", null, 3);

        // Insertar datos
        dbwriter = dataBaseHelper.getWritableDatabase();
        String sql2 = "INSERT INTO libros (categoria, titulo, autor, idioma, fecha_lectura_ini, fecha_lectura_fin, prestado_a, valoracion, formato, notas) \n" +
                "VALUES \n" +
                "('Fantasía', 'El Hobbit', 'J.R.R. Tolkien', 'Español', 1672531200000, 1675123200000, NULL, 4.8, 'Físico', 'Un clásico de la fantasía'),\n" +
                "('Realismo Mágico', 'Cien años de soledad', 'Gabriel García Márquez', 'Español', 1651363200000, 1653955200000, 'Juan Pérez', 4.9, 'Digital', 'Gran obra de la literatura latinoamericana');";
        dbwriter.execSQL(sql2);


        // Consultar datos
        dbreader = dataBaseHelper.getReadableDatabase();
        String sql = "SELECT * FROM libros";
        datos = new ArrayList<>();
        Cursor cursor = dbreader.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String categoria = cursor.getString(1);
            String titulo = cursor.getString(2);
            String autor = cursor.getString(3);
            Libro libro = new Libro(id, categoria, titulo, autor);
            datos.add(libro);
        }
        cursor.close();
        adaptador = new AdaptadorLibros(this, datos);
        

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
        ;
    }
}