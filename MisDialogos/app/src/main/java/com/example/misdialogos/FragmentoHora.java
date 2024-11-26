package com.example.misdialogos;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentoHora extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Hora hora;
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        hora=(Hora) getActivity();
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        // Creamos el TimePickerDialog con los parámetros correspondientes
        TimePickerDialog capturaHora= new TimePickerDialog(getActivity(), this::onTimeSet, 12, 00, true);
        // El último parámetro "true" indica si es en formato de 24 horas (false para 12 horas)
        return capturaHora;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        hora.pasarHora(hourOfDay,minute);
    }
    public interface Hora{
        public void pasarHora(int hora, int min);
    }
}
