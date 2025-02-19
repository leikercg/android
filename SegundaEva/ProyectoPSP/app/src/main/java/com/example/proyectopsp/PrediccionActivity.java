package com.example.proyectopsp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PrediccionActivity extends AppCompatActivity {

    TextView textViewTemperatura, textViewTemperaturaMin, textViewTemperaturaMax, textViewFecha, textViewCiudad;
    Button btnGuardarCiudad;
    ListView listViewHoras; // para mostrar la información horaria
    String API_KEY = "qZMYJVlhIy75nLfS";
    DatabaseHelper dbHelper;
    double latitud, longitud;
    String nombreCiudad;
    boolean estaGuardada, esDia, esNublado;
    ImageView foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_prediccion);

        // inicializar vistas
        textViewTemperatura = findViewById(R.id.textViewTemperatura);
        textViewTemperaturaMin = findViewById(R.id.textViewTemperaturaMin);
        textViewTemperaturaMax = findViewById(R.id.textViewTemperaturaMax);
        textViewCiudad = findViewById(R.id.textViewCiudad);
        textViewFecha = findViewById(R.id.textViewFecha);
        btnGuardarCiudad = findViewById(R.id.btnGuardarCiudad);
        listViewHoras = findViewById(R.id.listViewHoras);
        foto = findViewById(R.id.imageViewFoto);

        // incializar helper
        dbHelper = new DatabaseHelper(this);

        // obtener datos de la actividad anterior
        latitud = getIntent().getDoubleExtra("LATITUD", 0.0);
        longitud = getIntent().getDoubleExtra("LONGITUD", 0.0);
        nombreCiudad = getIntent().getStringExtra("CIUDAD");
        String datosClima = getIntent().getStringExtra("DATOS_CLIMA");

        textViewCiudad.setText(nombreCiudad);
        estaGuardada = comprobarBaseDatos(nombreCiudad);
        actualizarBoton();

        if (datosClima != null) {
            try {
                JSONObject jsonObject = new JSONObject(datosClima);
                mostrarDatosClima(jsonObject);
            } catch (Exception e) {
                Log.e("Error", "JSON NULLLLLLLL", e);
            }
        }
    }

    public void mostrarDatosClima(JSONObject jsonObject) {
        try {
            JSONObject dataCurrent = jsonObject.getJSONObject("data_current");
            JSONObject dataDay = jsonObject.getJSONObject("data_day");

            //la temperatura actual
            String temperaturaActual = dataCurrent.optString("temperature", "xx");
            if (temperaturaActual.length() >= 4) {
                textViewTemperatura.setText("Tº: " + temperaturaActual.substring(0, 4) + "°C");
            } else {
                textViewTemperatura.setText("Tº: " + temperaturaActual + "°C");
            }

            // fechas y temperaturas
            JSONArray fechas = dataDay.getJSONArray("time");
            JSONArray tempMinArray = dataDay.getJSONArray("temperature_min");
            JSONArray tempMaxArray = dataDay.getJSONArray("temperature_max");

            textViewTemperaturaMin.setText("Tº Min: " + tempMinArray.getString(0) + "°C");
            textViewTemperaturaMax.setText("Tº Max: " + tempMaxArray.getString(0) + "°C");
            textViewFecha.setText("Fecha: " + fechas.getString(0));



            // Crear objetos JSON en función a lo devuelto
            JSONObject data1h = jsonObject.getJSONObject("data_1h");
            JSONArray tiempos = data1h.getJSONArray("time");
            JSONArray temperaturas = data1h.getJSONArray("temperature");
            JSONArray precProbabilidad = data1h.getJSONArray("precipitation_probability");
            JSONArray totalNubes = data1h.getJSONArray("totalcloudcover");

            // Obtener la hora actual
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH");
            String horaActual = sdf.format(new java.util.Date());

            // El índice de la hora actual
            int indiceHoraActual = 0;
            for (int i = 0; i < tiempos.length(); i++) {
                String horaTiempos = tiempos.getString(i).substring(11, 13); // recoger solo HH
                if (horaTiempos.equals(horaActual)) {
                    indiceHoraActual = i;
                    break;
                } else if (horaTiempos.compareTo(horaActual) > 0) {
                    indiceHoraActual = Math.max(0, i - 1); // cogemos la hora pasada
                    break;
                }
            }

            // comprobar nubes y luz
            int isDaylight = dataCurrent.optInt("isdaylight", 1);
            esDia = isDaylight == 1;
            int nubes = totalNubes.getInt(indiceHoraActual);
            esNublado = nubes >= 70;

            // tiempo por horas
            ArrayList<String> listaHoras = new ArrayList<>();
            for (int i = 0; i < tiempos.length(); i++) {
                String horaFormateada = tiempos.getString(i).substring(11, 16);
                double temp = temperaturas.getDouble(i);
                int probLluvia = precProbabilidad.getInt(i);
                String item = horaFormateada + " - Temp: " + temp + "°C - Prob. lluvia: " + probLluvia + "%";
                listaHoras.add(item);
            }

            // actualizar el ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(PrediccionActivity.this,
                    android.R.layout.simple_list_item_1, listaHoras);
            listViewHoras.setAdapter(adapter);

            // mostrar imagen según el clima
            if (esDia) {
                if (esNublado) {
                    foto.setImageResource(R.drawable.dia_nublado);
                } else {
                    foto.setImageResource(R.drawable.dia_soleado);
                }
            } else {
               if (esNublado) {
                   foto.setImageResource(R.drawable.noche_nublada);
               } else {
                   foto.setImageResource(R.drawable.noche_despejada);
               }
            }

        } catch (Exception e) {
            Log.e("API", "Error", e);
        }
    }

    public void clickGuardarCiudad(View view) {
        if (estaGuardada) {
            eliminarCiudad(nombreCiudad);
            btnGuardarCiudad.setText("Guardar ciudad");
        } else {
            guardarCiudad(nombreCiudad, latitud, longitud);
            btnGuardarCiudad.setText("Eliminar ciudad");
        }
        estaGuardada = !estaGuardada;
        actualizarBoton();
    }

    private boolean comprobarBaseDatos(String ciudad) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("ciudades", new String[]{"nombre"}, "nombre = ?", new String[]{ciudad}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private void guardarCiudad(String ciudad, double lat, double lon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", ciudad);
        values.put("latitud", lat);
        values.put("longitud", lon);
        db.insert("ciudades", null, values);
    }

    private void eliminarCiudad(String ciudad) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("ciudades", "nombre = ?", new String[]{ciudad});
    }

    public void actualizarBoton() {
        if (estaGuardada){
            btnGuardarCiudad.setText("Eliminar ciudad");
        }else{
            btnGuardarCiudad.setText("Guardar ciudad");
        }
    }


}
