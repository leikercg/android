package com.example.hipotenochas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentoConfiguracion extends DialogFragment {

    TamanyoLayout tamanyoLayout;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        tamanyoLayout = (TamanyoLayout ) getActivity();

        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle("CONFIGURACIÓN HIPOTENOCHAS");
        ventana.setMessage("Selecciona la dificultad del juego: ");

        RadioGroup radioGroup = new RadioGroup(getContext());
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        RadioButton rbPrincipiante = new RadioButton(getContext());
        rbPrincipiante.setText("Principiante");

        RadioButton rbAmateur = new RadioButton(getContext());
        rbAmateur.setText("Amateur");

        RadioButton rbAvanzado = new RadioButton(getContext());
        rbAvanzado.setText("Avanzado");

        // Añadir los RadioButtons al RadioGroup
        radioGroup.addView(rbPrincipiante);
        radioGroup.addView(rbAmateur);
        radioGroup.addView(rbAvanzado);

        ventana.setView(radioGroup);

        ventana.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(rbPrincipiante.isChecked()) {
                    tamanyoLayout.tamanyoSeleccionado(1);
                } else if(rbAmateur.isChecked()) {
                    tamanyoLayout.tamanyoSeleccionado(2);
                } else if(rbAvanzado.isChecked()) {
                    tamanyoLayout.tamanyoSeleccionado(3);
                }
            }
        });
        return ventana.create();
    }

    public interface TamanyoLayout {
        void tamanyoSeleccionado(int i);
    }
}