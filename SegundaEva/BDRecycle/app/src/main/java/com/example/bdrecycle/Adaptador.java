package com.example.bdrecycle;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.Micontenedor> implements View.OnClickListener { //paara crear, vincular ygestionar cad item
    Context contexto; // para definir el contexto
    ArrayList<DatosPersonales> lista; //lista de datos que se mostraran
    View.OnClickListener escuchador; // para escuchar la main, el click de cada item

    public Adaptador(Context contexto, ArrayList<DatosPersonales> lista, View.OnClickListener escuchador) {
        this.contexto = contexto;
        this.lista = lista;
        this.escuchador=escuchador;
    }

    @NonNull
    @Override
    public Micontenedor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Crea el contenedor

        // Convertir ek xml a vista
        LayoutInflater inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vItem = inflador.inflate(R.layout.item_layout, parent, false);

        // Asifna el listener al cada item
        vItem.setOnClickListener(this); // Por el método implementado por la interfaz

        // Crear el contenedor, que referencia a las vistas del item
        return new Adaptador.Micontenedor(vItem);
    }

    @Override
    public void onBindViewHolder(@NonNull Micontenedor holder, int position) {
        // Se extraen los datos y se asignan a las vistas del item
        holder.tvNombre.setText(lista.get(position).getNombre());
        holder.tvEdad.setText((lista.get(position).getEdad() + "")); // por que tiene que ser string
        holder.imagen.setImageResource(R.mipmap.ic_avion_round);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    } // Devuelve el numero de elementos de la lista

    @Override
    public void onClick(View view) { // Metodo implemtado por la interfaz, al ser clicado en el item
        if (escuchador != null) {
            escuchador.onClick(view);
        }
    }

    public class Micontenedor extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener { // Clase interna, para menus contextuales implementar esto

        // Atributos
        TextView tvNombre, tvEdad;
        ImageView imagen;

        // Constructor
        public Micontenedor(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.textViewNombre);
            tvEdad = itemView.findViewById(R.id.textViewEdad);
            imagen = itemView.findViewById(R.id.imageView);
            itemView.setOnCreateContextMenuListener(this); // asignar el listener del menu contextual
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(getAdapterPosition(),121,0,"BORRAR");
            contextMenu.add(getAdapterPosition(),122,1,"EDITAR");
        }
    }

}
