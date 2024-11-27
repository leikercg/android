package com.example.hipotenochasclass;



import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class IconoFragment extends DialogFragment {

    IconoInterface iconoInterface;
    Spinner spinnerIcono;
    String seleccionado;

    @Override
    public void onAttach(@NonNull Context context) { // Aqui iniciamos el procedimiento.
        super.onAttach(context);
        iconoInterface = (IconoInterface) getActivity(); // Aquí obtenemos la actividad principal
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        LayoutInflater inflador = getActivity().getLayoutInflater();
        View vistaVentana = inflador.inflate(R.layout.icono, null);//  Creamos la vista con el inflador y el XML
        ventana.setTitle("Selecione el icono");
        ventana.setView(vistaVentana); //La vista se crea apartir de XML usando el inflador, establecemos una vista

        spinnerIcono=vistaVentana.findViewById(R.id.spinnerIcono);



        // Adaptador en JAVA
        String[] datos = new String[]{"Elem1", "Elem2", "Elem3", "Elem4", "Elem5"};
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos);
        spinnerIcono.setAdapter(adaptador);


        // Adaptador desde Recursos
        /*
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.datos_lista, android.R.layout.simple_list_item_1);
        lista.setAdapter(adaptador);*/

        //getApplicationContext, por que estamos dentro de un contexto, REvisar esto con mainactivity
        spinnerIcono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), datos[i], Toast.LENGTH_SHORT).show();
                seleccionado=datos[i].toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        ventana.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel(); //Nombre que tiene el parametro, para cancelar.
            }
        });

        ventana.setNegativeButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                iconoInterface.icono(seleccionado);
            }
        });

        return ventana.create();
    }

    public interface IconoInterface {
        public void icono(String icono);
    }

}