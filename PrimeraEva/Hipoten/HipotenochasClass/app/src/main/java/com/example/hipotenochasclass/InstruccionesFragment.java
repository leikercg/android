package com.example.hipotenochasclass;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class InstruccionesFragment extends DialogFragment {

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        LayoutInflater inflador = getActivity().getLayoutInflater();
        View vistaVentana = inflador.inflate(R.layout.instruciones, null);//  Creamos la vista con el inflador y el XML
        ventana.setTitle("Entrada de datos");
        ventana.setView(vistaVentana); //La vista se crea apartir de XML usando el inflador, establecemos una vista




        ventana.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel(); //Nombre que tiene el parametro, para cancelar.
            }
        });

        ventana.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return ventana.create();// Devolvemos la ventana creada
    }
}
