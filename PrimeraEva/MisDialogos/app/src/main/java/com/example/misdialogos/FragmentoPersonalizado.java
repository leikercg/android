package com.example.misdialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class FragmentoPersonalizado extends DialogFragment {

    //Datos datos; para hacerlo con 1
    DatoEdad datoEdad;
    DatoNombre datoNombre;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        //datos = (Datos) getActivity();
        datoEdad = (DatoEdad) getActivity();
        datoNombre = (DatoNombre) getActivity();

    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());

        // Para asociar el layout personalizado con el fragmento
        LayoutInflater inflador = getActivity().getLayoutInflater();
        View vistaVentana = inflador.inflate(R.layout.dialogo_personalizado, null);//  Creamos la vista con el inflador y el XML
        ventana.setView(vistaVentana); //La vista se crea apartir de XML usando el inflador, establecemos una vista

        ventana.setTitle("Entrada de datos");

        EditText etNombre = vistaVentana.findViewById(R.id.nombre);
        EditText etEdad = vistaVentana.findViewById(R.id.edad);

        ventana.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel(); //Nombre que tiene el parametro, para cancelar.
            }
        });

        ventana.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

               /* datos.pasarDatos(etNombre.getText().toString(), Integer.parseInt(etEdad.getText().toString()));
                dialogInterface.cancel(); // Para economizar recursos.*/
                datoEdad.pasarDatosEdad(Integer.parseInt(etEdad.getText().toString()));
                datoNombre.pasarDatosNombre(etNombre.getText().toString());
            }
        });
        return ventana.create();// Devolvemos la ventana creada
    }

    /*public interface Datos {
        public void pasarDatos (String nombre,  int edad);
    }*/

    public interface DatoEdad {

        public void pasarDatosEdad(int edad);
    }

    public interface DatoNombre {
        public void pasarDatosNombre(String nombre);
    }
}
