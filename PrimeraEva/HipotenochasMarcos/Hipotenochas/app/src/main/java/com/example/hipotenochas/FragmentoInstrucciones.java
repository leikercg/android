package com.example.hipotenochas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentoInstrucciones extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle("INSTRUCCIONES HIPOTENOCHAS");
        ventana.setMessage("El juego es tipo buscaminas: \n" +
                "Cuando pulsas en una casilla, sale un número que identifica cuántas hipotenochas hay alrededor: Ten cuidado porque si pulsas en una casilla que tenga" +
                " una hipotenocha, haz un click largo en una casilla donde no hay una hipotenocha porque perderás. Ganas una vez hayas encontrado todas las hipotenochas.");
        ventana.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return ventana.create();
    }
}
