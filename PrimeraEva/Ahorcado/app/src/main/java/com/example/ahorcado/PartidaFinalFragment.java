package com.example.ahorcado;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class PartidaFinalFragment extends DialogFragment {

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana= new AlertDialog.Builder(getActivity());
        ventana.setTitle("HAS PERDIDO!!");
        ventana.setMessage("Diálogo que utiliza un fragmento");
        // TODO: 29/10/2024   Poner ícono





        // Recuperar los datos pasados a través del Bundle
        Bundle args = getArguments();
        if (args != null) {
            String palabraSeleccionada = args.getString("palabraSeleccionada");
            int contadorFallos = args.getInt("contadorFallos");

            // Usar los datos en el fragmento (por ejemplo, mostrar en el mensaje)
            ventana.setTitle("HAS PERDIDO!!");
            ventana.setMessage("La palabra era: " + palabraSeleccionada + "\nFallos: " + contadorFallos);
        }




        ventana.setNegativeButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return ventana.create();
    }
}
