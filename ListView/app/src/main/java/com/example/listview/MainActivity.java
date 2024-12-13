package com.example.listview;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
//1. Crear ListView
//2. Crear los datos
//3. Crar adaptador y asjuntarlo
//4. Crear clase para el item
//5. Crear layout XML



/*MENU CONTEXTUAL:
-Definir la estructura del menu ...XML
-COnverir el menú en lista ... inflate
-Asociar el menú a la vista o cualquier cosa
-Definir listener.
* */

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ListView listaOpciones;
   /* final String[] datos =
            new String[]{"Elem1", "Elem2", "Elem3", "Elem4", "Elem5"}; // Datos globale
            */

    private Titular[] datos = //datos globales
            new Titular[]{
                    new Titular("Título 1", "Subtítulo largo 1"),
                    new Titular("Título 2", "Subtítulo largo 2"),
                    new Titular("Título 3", "Subtítulo largo 3"),
                    new Titular("Título 4", "Subtítulo largo 4"),
                    new Titular("Título 5", "Subtítulo largo 5"),
                    new Titular("Título 6", "Subtítulo largo 6"),
                    new Titular("Título 7", "Subtítulo largo 7"),
                    new Titular("Título 8", "Subtítulo largo 8"),
                    new Titular("Título 9", "Subtítulo largo 9"),
                    new Titular("Título 10", "Subtítulo largo 10"),
                    new Titular("Título 11", "Subtítulo largo 11"),
                    new Titular("Título 12", "Subtítulo largo 12"),
                    new Titular("Título 13", "Subtítulo largo 13"),
                    new Titular("Título 14", "Subtítulo largo 14"),
                    new Titular("Título 15", "Subtítulo largo 15")};


    AdaptadorTitulares adaptador;// Creamos un adaptador

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        listaOpciones = findViewById(R.id.listView); // Recuperamos la lista

        registerForContextMenu(listaOpciones); //asociamos la lista al menu


        /*ArrayAdapter<String> adaptador = // Creamos un adaptador
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, datos);*/

        adaptador = new AdaptadorTitulares(this, datos);// Creamos un adaptador

        listaOpciones.setAdapter(adaptador); // Le adjudicamos el adaptador

        listaOpciones.setOnItemClickListener(new AdapterView.OnItemClickListener() { // Agregar listener
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                //Se recibe el adaptador, la vista y el elemento que se ha clicado

                //Alternativa 1: Adaptador y posicion
                // String opcionSeleccionada =
                //        ((Titular)adapterView.getItemAtPosition(posicion)).getTitulo(); // Hay que hacer un cast

                //Alternativa 2: a traves de la vista (el propio item, sobre el que se ha pulsado)
                String opcionSeleccionada =
                        ((TextView) view.findViewById(R.id.etiqueta_titulo))// etiqueta título
                                .getText().toString();

                textView.setText("Opción seleccionada: " + opcionSeleccionada);

            }
        });

    }

    //////////////////////////////////////// CLASE INTERNA///////////////////
    class AdaptadorTitulares extends ArrayAdapter<Titular> { // Adaptador personalizado

        public AdaptadorTitulares(Context context, Titular[] datos) {
            super(context, R.layout.layout_item, datos); // Que layout mostrar
        }


        public View getView(int position, View convertView, ViewGroup parent) {// que ejecutar cada vez que se visualice un item

            ViewHolder contendor;
            View item = convertView;
            if (item == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                item = inflater.inflate(R.layout.layout_item, null);

                contendor = new ViewHolder();
                contendor.titulo = item.findViewById(R.id.etiqueta_titulo);
                contendor.subttitulo = item.findViewById(R.id.etiqueta_subtitulo);
                item.setTag(contendor); // Me ahorro el finbyId.

            } else {
                contendor = (ViewHolder) item.getTag();
            }

            contendor.titulo.setText((datos[position].getTitulo()));
            contendor.subttitulo.setText((datos[position].getSubtitulo()));
           /* TextView lblTitulo = (TextView) item.findViewById(R.id.etiqueta_titulo);
            lblTitulo.setText(datos[position].getTitulo());

            TextView lblSubtitulo = (TextView) item.findViewById(R.id.etiqueta_subtitulo);
            lblSubtitulo.setText(datos[position].getSubtitulo());*/

            return (item);
        }
    }

    //////////////////////////////////////// OTRA CLASE INTERNA///////////////////
    static class ViewHolder {
        TextView titulo;
        TextView subttitulo;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflador = getMenuInflater();

        AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) menuInfo;

        //menu.setHeaderTitle((AdaptadorTitulares) listaOpciones.getAdapter().getItem(informacionItem.position).toString());
        inflador.inflate(R.menu.menu_contextual, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo informacionItem = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        textView.setText(informacionItem.position + 1 + "\t");

        if (item.getItemId() == R.id.item_1) {
            textView.append("Opción 1");
        } else if (item.getItemId() == R.id.item_2) {
            textView.append("Opción 2");
        }

        // return super.onContextItemSelected(item);// devuelve el item seleccionado del menu
        return true;
    }
}