package com.example.setpreferencesactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.View;

import androidx.activity.EdgeToEdge;

public class Opcionesctivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      addPreferencesFromResource(R.xml.preferencias_layout);

    }

}
