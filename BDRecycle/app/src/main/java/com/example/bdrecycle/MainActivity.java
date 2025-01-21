package com.example.bdrecycle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView vistaRecycler;
    ArrayList<DatosPersonales> lista = new ArrayList<DatosPersonales>();
    Adaptador adaptador;
    TextView tv;
    ActivityResultLauncher<Intent> lanzadorAlta; //definimos en lanzador de la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        inicializaLista();// Iniciar la lista con unos datos basicos
        vistaRecycler = findViewById(R.id.recyclerView);
        adaptador = new Adaptador(this, lista,this); // Recibe un contexto y un ArrayList, recibe el paremtro para escuchar el click esperado
        tv = findViewById(R.id.textView);
        vistaRecycler.setLayoutManager(new LinearLayoutManager(this));// Por que tambien puede ser usado para manejar Grids
        vistaRecycler.setAdapter(adaptador);

        // Inicializamos el lanzador de lactividad
        lanzadorAlta = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult resultado) { // Que hacer con los datos que vuelven de la actividadAlra
                        if(resultado.getResultCode() == RESULT_OK){
                            String nombre = resultado.getData().getStringExtra("NOMBRE");
                            int edad = Integer.parseInt(resultado.getData().getStringExtra("EDAD"));

                            lista.add(new DatosPersonales(nombre,edad)); // Agregamos los nuevo elementos
                            adaptador.notifyDataSetChanged(); // Refrescamos el adaptador con la lista actualizada

                        }
                    }
                });
    }

    private void inicializaLista() { // metodo para inicializar lista
        lista.add(new DatosPersonales("Pepito",23));
        lista.add(new DatosPersonales("Jorgito",13));
        lista.add(new DatosPersonales("Juanito",14));
        lista.add(new DatosPersonales("JAimito",15));
    }

    @Override
    public void onClick(View view) {
        tv.setText(lista.get(vistaRecycler.getChildAdapterPosition(view)).getNombre()); // obtener el nombre
    }


    ////////// CREAR MENU
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_pricipal,menu);
        return true;
    }

    ///// OPCIONES DE MENU
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.item_alta){
            tv.setText("ALTA");
            lanzadorAlta.launch (new Intent(this, AltaActivity.class));
        }
        return true;
    }


    //QUE HACER EN EL CONTEXTUAL
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) { // Que hacer en el menú contextual
        switch (item.getItemId()){
            case 121:
                tv.setText("BORRAR ");
                lista.remove(item.getGroupId());// optenemos la posicion de la lista y la borra
                adaptador.notifyDataSetChanged(); // actualizamos la vista
                return true;
            case 122:
                tv.setText("EDITAR");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}