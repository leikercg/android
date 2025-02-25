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
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle("DIÁLOGO CON FRAGMENTO");
        ventana.setMessage("Diálogo que utiliza un fragmento");
        ventana.setIcon(R.drawable.ic_launcher_foreground);

        // Botón "SÍ"
        ventana.setPositiveButton("SÍ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Presionaste SÍ", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón "NO"
        ventana.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Presionaste NO", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón "CANCELAR" (opcional)
        ventana.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "Diálogo cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        return ventana.create();
    }
}
