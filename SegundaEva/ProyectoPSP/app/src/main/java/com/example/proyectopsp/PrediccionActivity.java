package com.example.proyectopsp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PrediccionActivity extends AppCompatActivity {

    TextView textViewTemperatura, textViewTemperaturaMin, textViewTemperaturaMax,textViewFecha;
    final String API_KEY = "qZMYJVlhIy75nLfS";
    final String BASE_URL = "https://my.meteoblue.com/packages/basic-1h_basic-day_current";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prediccion);

        textViewTemperatura= findViewById(R.id.textViewTemperatura);
        textViewTemperaturaMin= findViewById(R.id.textViewTemperaturaMin);
        textViewTemperaturaMax= findViewById(R.id.textViewTemperaturaMax);
        textViewFecha= findViewById(R.id.textViewFecha);

        double latitud = getIntent().getDoubleExtra("LATITUD", 0.0);
        double longitud = getIntent().getDoubleExtra("LONGITUD", 0.0);

        new ObtenerClimaTask().execute(latitud, longitud);
    }

    class ObtenerClimaTask extends AsyncTask<Double, Void, String[]> {
        @Override
        protected String[] doInBackground(Double... coords) {
            if (coords.length < 2) {
                return null;
            }

            double lat = coords[0];
            double lon = coords[1];

            try {
                String urlString = "https://my.meteoblue.com/packages/basic-1h_basic-day_current?apikey=qZMYJVlhIy75nLfS"
                        + "&lat=" + lat + "&lon=" + lon + "&format=json";
                URL url = new URL(urlString);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                JSONObject jsonObject = new JSONObject(buffer.toString());

                // Obtener temperatura actual
                String temperaturaActual = jsonObject.getJSONObject("data_current").getString("temperature");

                // Obtener temperatura mínima y máxima del día actual
                JSONArray fechas = jsonObject.getJSONObject("data_day").getJSONArray("time");
                JSONArray tempMinArray = jsonObject.getJSONObject("data_day").getJSONArray("temperature_min");
                JSONArray tempMaxArray = jsonObject.getJSONObject("data_day").getJSONArray("temperature_max");

                String temperaturaMin = tempMinArray.getString(0); // Día actual (índice 0)
                String temperaturaMax = tempMaxArray.getString(0); // Día actual (índice 0)
                String fechaActual = fechas.getString(0); // Día actual (índice 0)

                return new String[]{temperaturaActual, temperaturaMin, temperaturaMax, fechaActual};

            } catch (Exception e) {
                Log.e("API_ERROR", "Error en la consulta de clima", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] temperaturas) {
            if (temperaturas != null) {
                textViewTemperatura.setText("Temperatura actual: " + temperaturas[0] + "°C");
                textViewTemperaturaMin.setText("Temperatura mínima: " + temperaturas[1] + "°C");
                textViewTemperaturaMax.setText("Temperatura máxima: " + temperaturas[2] + "°C");
                textViewFecha.setText("Fecha: " + temperaturas[3]);
            } else {
                textViewTemperatura.setText("Error obteniendo la temperatura");
            }
        }
    }


}
