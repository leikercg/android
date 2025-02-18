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

import java.util.Calendar;

public class FragmentoHora extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Hora hora;
    public void onAttach(@NonNull Context context) { // esto adjunta la hora a la interface
        super.onAttach(context);
        hora=(Hora) getActivity();
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // Hora actual en formato 24 horas
        int minute = calendar.get(Calendar.MINUTE); // Minuto actual

        // Creamos el TimePickerDialog con los parámetros correspondientes
/*
        TimePickerDialog capturaHora= new TimePickerDialog(getActivity(), this::onTimeSet, 12, 00, true);*/

        return new TimePickerDialog(getActivity(), this::onTimeSet, hour, minute, true);

        // El último parámetro "true" indica si es en formato de 24 horas (false para 12 horas)
        //return capturaHora;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) { //evento que ocurre cuando el usuario selecciona la hora
        //Pasa los datos seleccionados a la interfaz implementada en la main
        hora.pasarHora(hourOfDay,minute); // Se pasan los datos por la interface
    }
    public interface Hora{
        public void pasarHora(int hora, int min);
    }
}
