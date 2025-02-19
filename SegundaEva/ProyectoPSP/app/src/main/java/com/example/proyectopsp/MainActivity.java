package com.example.proyectopsp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    DatabaseHelper dbHelper;
    ListView listViewGuardadas;
    ArrayAdapter<String> adapterGuardadas;
    List<String> ciudadesGuardadas = new ArrayList<>();

    ListView listView;
    EditText editText;
    Handler handler = new Handler(Looper.getMainLooper()); // para manejar el retraso
    Runnable workRunnable; // hilo de trabajo para el retraso
    String API_KEY = "qZMYJVlhIy75nLfS";
    String BASE_URL = "https://www.meteoblue.com/en/server/search/query3";

    // lista para almacenar latitud y longitud de las ciudades
    List<Double[]> listaCoordenadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        listView = findViewById(R.id.listView);
        listViewGuardadas = findViewById(R.id.listViewGuardadas);
        dbHelper = new DatabaseHelper(this); // parea manejar la base de datos

        cargarCiudadesGuardadas();

        editText.addTextChangedListener(new TextWatcher() { // usado para detectar cambios en el texto, implementa 3 métodos
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { // que hacer cuando está cambiando el texto
                if (workRunnable != null) {
                    handler.removeCallbacks(workRunnable); // cancelar la búsqueda
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) { // que hacer cuando termina de escribir
                workRunnable = () -> {
                    String busqueda = editable.toString().trim();
                    if (!busqueda.isEmpty()) {
                        buscarCiudades(busqueda); // Realizar la búsqueda
                    }
                };
                handler.postDelayed(workRunnable, 1000); // retraso de 1 segundo, para evitar consultas innecesarias
            }
        });

        // agregar listener para capturar la ciudad seleccionada en el ListView
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Double[] coordenadas = listaCoordenadas.get(position); // obtener latitud y longitud de la ciudad seleccionada
            double latitud = coordenadas[0];
            double longitud = coordenadas[1];

            String ciudadSeleccionada = (String) parent.getItemAtPosition(position);

            // consultae la api de geografia
            new ObtenerClimaSegundoPlano(latitud, longitud, ciudadSeleccionada).execute();
        });

        listViewGuardadas.setOnItemClickListener((parent, view, position, id) -> { // lista de ciudades guardadas
            String ciudadSeleccionada = ciudadesGuardadas.get(position);
            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                    "SELECT latitud, longitud FROM ciudades WHERE nombre = ?", new String[]{ciudadSeleccionada});
            if (cursor.moveToFirst()) {
                double latitud = cursor.getDouble(0);
                double longitud = cursor.getDouble(1);
                // consultar api
                new ObtenerClimaSegundoPlano(latitud, longitud, ciudadSeleccionada).execute();
            }
            cursor.close();
        });
    }
    public void cargarCiudadesGuardadas() { // metodo para cargar las ciudades guardadas en la base de datos
        ciudadesGuardadas.clear(); // limpiar la lista antes de recargar
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select nombre from ciudades", null);

        while (cursor.moveToNext()) {
            ciudadesGuardadas.add(cursor.getString(0));
        }
        cursor.close();

        if (adapterGuardadas == null) { // si el adaptador aun no está inicializado
            adapterGuardadas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ciudadesGuardadas);
            listViewGuardadas.setAdapter(adapterGuardadas);
        } else {
            adapterGuardadas.notifyDataSetChanged(); // si el adaptador ya esat inicializado se actualiza
        }
    }

    @Override
    protected void onResume() {// se ejecuta cada vez que se regresa a la pantalla main
        super.onResume();
        cargarCiudadesGuardadas();
    }
    
    public void buscarCiudades(String query) { // crea una nueva tarea en segundo plano para realizar la búsqueda
        new buscarCiudadesTask().execute(query);
    }

    // valor que recibe doInBackground desde el execute, valor recibe onProgressUodate desde publishProgress, valor que recibe onPostExecute desde doInBackground
    class buscarCiudadesTask extends AsyncTask<String, Void, List<String>> { // Tarea en segundo plano
        @Override
        protected List<String> doInBackground(String... texto) { // metodo que se ejecuta en segundo plano
            String query = texto[0];
            List<String> ciudadesList = new ArrayList<>(); // para guardar las ciudades

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
                    Log.e("API", "Error: " + responseCode);
                    return ciudadesList;
                }

                // leer la respuesta JSON
                InputStream in = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                // convertir la respuesta JSON a un objeto JSON
                JSONObject jsonObject = new JSONObject(buffer.toString());
                if (jsonObject.has("results")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    // limpiar la lista de coordenadas antes de agregar nuevas
                    listaCoordenadas.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonCiudad = jsonArray.getJSONObject(i);
                        String nombre = jsonCiudad.optString("name", "xx");
                        String pais = jsonCiudad.optString("country", "xx");
                        String comunidad = jsonCiudad.optString("admin1", "xx");

                        // obtener latitud y longitud
                        double latitud = jsonCiudad.optDouble("lat", 0.0);
                        double longitud = jsonCiudad.optDouble("lon", 0.0);

                        // guardar coordenadas en la lista
                        listaCoordenadas.add(new Double[]{latitud, longitud});

                        // Solo mostrar el nombre, país y comunidad en el ListView
                        String infoFinal = nombre + ", " + pais + ", " + comunidad;
                        ciudadesList.add(infoFinal);
                    }
                } else {
                    Log.d(" API", "Sin resultados': " + buffer.toString());
                }

            } catch (Exception e) {
                Log.e("API", "Error", e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e("API", "Error", e);
                    }
                }
            }
            // devolver la lista de ciudades
            return ciudadesList;
        }

        @Override
        protected void onPostExecute(List<String> result) {
            if (result != null && !result.isEmpty()) {
                actualizarListView(result);
            } else {
                Log.e("API", "Sin resultadoss");
            }
        }
    }

    public void actualizarListView(List<String> ciudades) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ciudades);
        listView.setAdapter(adapter);
    }

    class ObtenerClimaSegundoPlano extends AsyncTask<Void, Void, JSONObject> {
        double latitud;
        double longitud;
        String ciudadSeleccionada;

        public ObtenerClimaSegundoPlano(double latitud, double longitud, String ciudadSeleccionada) {
            this.latitud = latitud;
            this.longitud = longitud;
            this.ciudadSeleccionada = ciudadSeleccionada;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                String urlString = "https://my.meteoblue.com/packages/basic-1h_basic-day_current_clouds-1h?apikey=" + API_KEY +
                        "&lat=" + latitud + "&lon=" + longitud + "&format=json&tz=GMT&forecast_days=1";
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                // convertir el buffer en objeto JSON
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder buffer = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return new JSONObject(buffer.toString()); // devolver el JSON

            } catch (Exception e) {
                Log.e("API", "Error", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            if (jsonObject != null) {
                // preparar ek intent y enviarlo
                Intent intent = new Intent(MainActivity.this, PrediccionActivity.class);
                intent.putExtra("LATITUD", latitud);
                intent.putExtra("LONGITUD", longitud);
                intent.putExtra("CIUDAD", ciudadSeleccionada);
                intent.putExtra("DATOS_CLIMA", jsonObject.toString()); // solo se puede pasar como string


                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "VALOR NULLLLLLL", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
