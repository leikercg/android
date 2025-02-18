package com.example.tresenraya;

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

public class GanadorFragment extends DialogFragment {

    DatoNombre instanciaDeinterfaceNombre;
    EditText editTextNombre;

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        instanciaDeinterfaceNombre = (DatoNombre) getActivity();

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana= new AlertDialog.Builder(getActivity());

        LayoutInflater inflador = getActivity().getLayoutInflater();
        View vistaVentana = inflador.inflate(R.layout.ganador_activity, null);//  Creamos la vista con el inflador y el XML
        ventana.setView(vistaVentana); //La vista se crea apartir de XML usando el inflador, establecemos una vista

       
        ventana.setTitle("Final de la partida");
        ventana.setMessage("Introduce tu nombre: ");


        editTextNombre=vistaVentana.findViewById(R.id.editTextNombre);

        ventana.setNegativeButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              if (editTextNombre.getText().toString().compareTo("")==0){
                  Toast.makeText(getActivity(), "Por favor ingrese su nombre", Toast.LENGTH_SHORT).show();
              }else {
                  instanciaDeinterfaceNombre.pasarNombre(editTextNombre.getText().toString());
              }
            }
        });
        return ventana.create();
    }



    public interface DatoNombre {

        void pasarNombre(String nombre);
    }
}
