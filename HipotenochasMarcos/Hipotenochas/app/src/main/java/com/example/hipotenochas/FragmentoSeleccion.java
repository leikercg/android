package com.example.hipotenochas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentoSeleccion extends DialogFragment {

    List<Item> datosHipotenocha;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Spinner lista = new Spinner(getActivity());

        datosHipotenocha = getLista();

        ItemAdapter adapter = new ItemAdapter(getActivity(), datosHipotenocha);
        lista.setAdapter(adapter);

        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle("CONFIGURACIÓN HIPOTENOCHAS");
        ventana.setMessage("Selecciona su personaje favorito: ");
        ventana.setView(lista);

        ventana.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        ventana.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        return ventana.create();
    }


    public List<Item> getLista() {
        List<Item> lista = new ArrayList<>(); // = new Item[] {new Item(R.drawable.abstracta, "Abstracta"), new Item(R.drawable.afortunada, "Afortunada"), new Item(R.drawable.camuflada, "Camuflada"), new Item(R.drawable.carinosa, "Cariñosa"), new Item(R.drawable.che, "Che"), new Item(R.drawable.colchonero, "Colchonero"), new Item(R.drawable.cule, "Cule"), new Item(R.drawable.deincognito, "De incógnito"), new Item(R.drawable.deportista, "Deportista")};
        lista.add(new Item(R.drawable.abstracta, "Abstracta"));
        lista.add(new Item(R.drawable.afortunada, "Afortunada"));
        lista.add(new Item(R.drawable.camuflada, "Camuflada"));
        lista.add(new Item(R.drawable.carinosa, "Cariñosa"));
        lista.add(new Item(R.drawable.che, "Che"));
        lista.add(new Item(R.drawable.cule, "Cule"));
        lista.add(new Item(R.drawable.deincognito, "De incógnito"));
        lista.add(new Item(R.drawable.deportista, "Deportista"));
        return lista;
    }

}
