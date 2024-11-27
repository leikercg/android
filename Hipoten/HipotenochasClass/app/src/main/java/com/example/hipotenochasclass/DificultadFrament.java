package com.example.hipotenochasclass;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DificultadFrament extends DialogFragment {

    DificultadSelecionada dificultadSelecionada;
    RadioGroup radioGroup;

    @Override
    public void onAttach(@NonNull Context context) { // Aqui iniciamos el procedimiento.
        super.onAttach(context);
        dificultadSelecionada = (DificultadSelecionada) getActivity(); // Aquí obtenemos la actividad principal
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        LayoutInflater inflador = getActivity().getLayoutInflater();
        View vistaVentana = inflador.inflate(R.layout.dificultad, null);//  Creamos la vista con el inflador y el XML
        ventana.setTitle("Selecione el nivel");
        ventana.setView(vistaVentana); //La vista se crea apartir de XML usando el inflador, establecemos una vista

        radioGroup=vistaVentana.findViewById(R.id.radioGroupDificultades);

        //AÑADIR UN LISTENER
        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Obtén el RadioButton seleccionado por su ID
                RadioButton seleccionado = group.findViewById(checkedId);

                if (seleccionado != null) {
                    // Obtén el texto del RadioButton seleccionado
                    String textoSeleccionado = seleccionado.getText().toString();
                    // Muestra un Toast con el texto de la opción seleccionada
                    Toast.makeText(getActivity(), "Seleccionado: " + textoSeleccionado, Toast.LENGTH_SHORT).show();
                }
            }
        });*/




        ventana.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel(); //Nombre que tiene el parametro, para cancelar.
            }
        });

        ventana.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                RadioButton selecionado= vistaVentana.findViewById(radioGroup.getCheckedRadioButtonId());//obtenemos el boton selecionado
                Toast.makeText(getActivity(), selecionado.getText().toString(), Toast.LENGTH_SHORT).show();
                dificultadSelecionada.nivel(selecionado.getText().toString()); // Aqui enviamos los datos por la interfaz
            }
        });

        return ventana.create();
    }

    public interface DificultadSelecionada {
        public void nivel(String nivel);
    }

}
