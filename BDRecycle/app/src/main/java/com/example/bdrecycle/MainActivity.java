package com.example.bdrecycle;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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
}