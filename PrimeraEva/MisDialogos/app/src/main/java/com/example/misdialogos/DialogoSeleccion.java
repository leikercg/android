package com.example.misdialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoSeleccion extends DialogFragment {
    IdiomaLista idiomaLista;

    @Override
    public void onAttach(@NonNull Context context) { // Aqui iniciamos el procedimiento.
        super.onAttach(context);
        idiomaLista = (IdiomaLista)getActivity(); // Aquí obtenemos la actividad principal
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String[] items = {"Español", "Japón", "China","Inglés"};
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle("DIÁLOGO DE IDIOMAS");
        ventana.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), items[i], Toast.LENGTH_SHORT).show();
                idiomaLista.idiomaSeleccionado(items[i],i);
            }
        });
        return ventana.create();
    }

    public interface IdiomaLista {
        public void idiomaSeleccionado(String idioma, int posicion);
    }
}
