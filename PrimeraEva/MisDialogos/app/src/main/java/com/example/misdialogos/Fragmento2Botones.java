package com.example.misdialogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class Fragmento2Botones extends DialogFragment {
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana= new AlertDialog.Builder(getActivity());
        ventana.setTitle("DIÁLOGO CON FRAGMENTO");
        ventana.setMessage("Diálogo que utiliza un fragmento");
        // TODO: 29/10/2024   Poner ícono
        ventana.setIcon(R.drawable.ic_launcher_foreground);
        ventana.setNegativeButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Mensaje desde fragmento", Toast.LENGTH_SHORT).show();
            }
        });
        return ventana.create();
    }
}
