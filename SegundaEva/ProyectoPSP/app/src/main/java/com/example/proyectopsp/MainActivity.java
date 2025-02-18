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

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    Handler handler = new Handler(Looper.getMainLooper()); // Para manejar el retraso
    Runnable workRunnable; // Hilo de trabajo para el retraso
    static final String API_KEY = "qZMYJVlhIy75nLfS";
    static final String BASE_URL = "https://www.meteoblue.com/en/server/search/query3";

    // Lista para almacenar latitud y longitud de las ciudades
    private final List<Double[]> coordenadasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);

        editText.addTextChangedListener(new TextWatcher() { // Usado para detectar cambios en el texto, implementa 3 métodos
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // Que hacer cuando está cambiando el texto
                if (workRunnable != null) {
                    handler.removeCallbacks(workRunnable); // Cancelar la búsqueda
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) { // Qué hacer cuando termina de escribir
                workRunnable = () -> {
                    String query = editable.toString().trim();
                    if (!query.isEmpty()) {
                        buscarCiudades(query); // Realizar la búsqueda
                    }
                };
                handler.postDelayed(workRunnable, 1000); // Retraso de 1 segundo, para evitar consultas innecesarias
            }
        });

        // Agregar listener para capturar la ciudad seleccionada en el ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Double[] coordenadas = coordenadasList.get(position); // Obtener latitud y longitud de la ciudad seleccionada
            double latitud = coordenadas[0];
            double longitud = coordenadas[1];

            Intent intent = new Intent(MainActivity.this, PrediccionActivity.class);
            intent.putExtra("LATITUD", latitud);
            intent.putExtra("LONGITUD", longitud);
            startActivity(intent);
            // Aquí puedes usar las coordenadas para hacer una consulta meteorológica
            Log.d("COORDENADAS", "Latitud: " + latitud + ", Longitud: " + longitud);
        });
    }

    public void buscarCiudades(String query) { // Crea una nueva tarea en segundo plano para realizar la búsqueda
        new BuscarCiudadesTask().execute(query);
    }

    class BuscarCiudadesTask extends AsyncTask<String, Void, List<String>> { // Tarea en segundo plano
        @Override
        protected List<String> doInBackground(String... texto) { // Método que se ejecuta en segundo plano
            String query = texto[0];
            List<String> ciudadesList = new ArrayList<>(); // Para guardar las ciudades

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                String urlString = BASE_URL + "?query=" + URLEncoder.encode(query, "UTF-8")
                        + "&apikey=" + API_KEY;
                URL url = new URL(urlString);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    Log.e("API_ERROR", "Error en la respuesta HTTP: " + responseCode);
                    return ciudadesList;
                }

                // Leer la respuesta JSON
                InputStream in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                // Convertir la respuesta JSON a un objeto JSON
                JSONObject jsonObject = new JSONObject(buffer.toString());
                if (jsonObject.has("results")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    // Limpiar la lista de coordenadas antes de agregar nuevas
                    coordenadasList.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonCiudad = jsonArray.getJSONObject(i);
                        String nombre = jsonCiudad.optString("name", "Desconocido");
                        String pais = jsonCiudad.optString("country", "N/A");
                        String comunidad = jsonCiudad.optString("admin1", "Desconocido");

                        // Obtener latitud y longitud
                        double latitud = jsonCiudad.optDouble("lat", 0.0);
                        double longitud = jsonCiudad.optDouble("lon", 0.0);

                        // Guardar coordenadas en la lista
                        coordenadasList.add(new Double[]{latitud, longitud});

                        // Solo mostrar el nombre, país y comunidad en el ListView
                        String infoFinal = nombre + ", " + pais + ", " + comunidad;
                        ciudadesList.add(infoFinal);
                    }
                } else {
                    Log.d("API_RESPONSE", "Respuesta JSON sin 'results': " + buffer.toString());
                }

            } catch (Exception e) {
                Log.e("API_ERROR", "Error en la conexión o JSON", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("API_ERROR", "Error cerrando el lector", e);
                    }
                }
            }
            // Devolver la lista de ciudades
            return ciudadesList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (result != null && !result.isEmpty()) {
                actualizarListView(result);
            } else {
                Log.e("API_ERROR", "No se obtuvo respuesta o no se encontraron ciudades.");
            }
        }
    }

    private void actualizarListView(List<String> ciudades) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ciudades);
        listView.setAdapter(adapter);
    }
}
