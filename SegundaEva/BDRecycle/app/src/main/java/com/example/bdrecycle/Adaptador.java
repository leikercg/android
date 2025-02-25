package com.example.bdrecycle;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.Micontenedor> implements View.OnClickListener { // Para crear, vincular y gestionar cada item
    Context contexto; // Para definir el contexto donde se utilizará el adaptador
    ArrayList<DatosPersonales> lista; // Lista de datos que se mostrarán en el RecyclerView
    View.OnClickListener escuchador; // Para escuchar la main, el click de cada item

    // Constructor del adaptador que recibe el contexto, la lista de datos y el listener de clics
    public Adaptador(Context contexto, ArrayList<DatosPersonales> lista, View.OnClickListener escuchador) {
        this.contexto = contexto;
        this.lista = lista;
        this.escuchador = escuchador;
    }

    @NonNull
    @Override // Metodo abstracto de Reciclerview.Adaptador, crea objetos de tipo Micontenedor
    public Micontenedor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Crea el contenedor

        // Convertir el XML del layout del item en una vista
        LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //
        View vItem = inflador.inflate(R.layout.item_layout, parent, false);

        // Asigna el listener a cada item para detectar clics en la lista
        vItem.setOnClickListener(this); // Utiliza el método implementado de la interfaz

        // Retorna un nuevo contenedor que referencia las vistas del item
        return new Adaptador.Micontenedor(vItem);
    }

    @Override // Metodo abstracto de Reciclerview.Adaptador
    public void onBindViewHolder(@NonNull Micontenedor holder, int position) { // Asigna datos a cada item
        // Se extraen los datos del objeto correspondiente en la lista y se asignan a las vistas del item
        holder.tvNombre.setText(lista.get(position).getNombre());
        holder.tvEdad.setText(lista.get(position).getEdad() + ""); // Convertir la edad a String
        holder.imagen.setImageResource(R.mipmap.ic_avion_round); // Asigna una imagen de recurso a cada item
    }

    @Override // Metodo abstracto de Reciclerview.Adaptador
    public int getItemCount() {
        return lista.size(); // Devuelve el número de elementos en la lista
    }

    @Override // Método implementado por la interfaz, ejecutado al hacer clic en un item
    public void onClick(View view) {
        if (escuchador != null) {
            escuchador.onClick(view); // Llama al listener externo pasado al adaptador
        }
        //  Permite que la MainActivity o cualquier otra clase que use el Adaptador maneje los clics en los ítems del RecyclerView.
    }

    // Clase interna para definir el contenedor de vistas de cada item
    public class Micontenedor extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        // Atributos que representan las vistas dentro del item
        TextView tvNombre, tvEdad;
        ImageView imagen;

        // Constructor de la clase contenedora
        public Micontenedor(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.textViewNombre); // Referencia a la vista de nombre
            tvEdad = itemView.findViewById(R.id.textViewEdad); // Referencia a la vista de edad
            imagen = itemView.findViewById(R.id.imageView); // Referencia a la vista de imagen
            itemView.setOnCreateContextMenuListener(this); // Asigna el listener para el menú contextual
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            // Crea un menu contextual
            // Agrega opciones al menú contextual
            contextMenu.add(getAdapterPosition(), 121, 0, "BORRAR"); // Opción para borrar
            contextMenu.add(getAdapterPosition(), 122, 1, "EDITAR"); // Opción para editar
            // pueden ser manejadas en la MainActivity con onContextItemSelected(MenuItem item).
        }
    }
}
