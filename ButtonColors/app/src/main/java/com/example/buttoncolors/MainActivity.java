package com.example.buttoncolors;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.DisplayCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.gridlayout.widget.GridLayout;
// Pimero crear gridlayout
// Segundo añadir los botones
// Colorear botones

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Point p;
    int altoPantalla, anchoPantalla;
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        p = new Point();
        Display pantallaDisplay = getWindowManager().getDefaultDisplay(); // Coger las dimensiones de la pantalla que se va a lanzar
        pantallaDisplay.getSize(p); // Se le pasa el punto de la clase point, que será quien almacena el valor.

        // Toast.makeText(this, "Ancho: "+ p.x+" Alto: "+p.y, Toast.LENGTH_SHORT).show();

        altoPantalla = p.y;
        anchoPantalla = p.x;

        gridLayout = findViewById(R.id.grilla_layout);// Inicializado, OJO REVISAR LA CLASE QUE SE IMPORTA

        añadirBotones();// función que añade los botones

    }

    public void añadirBotones() { //crear botones dinamicamente
        for (int i = 0; i < 18; i++) {
            Button b = new Button(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(anchoPantalla / 3, altoPantalla / 6); //Parametros a pasar al botón
            b.setLayoutParams(lp);// Para establecer los parámetros al botón en lugar de pasarlos por el constructor
            if (i < 17) {
                b.setText("Btn " + i);
            }

            if (i == 17) {
                b.setText("RESET");
            }

            b.setId(View.generateViewId()); //Genera la id del boton por si mismo, sin necesidaad de jugarnosla nosotros
            b.setOnClickListener(this); // La clase debera implementar el onclickListener
            gridLayout.addView(b); // Añadir el botón al gridlayout
        }
        
        colorear();
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view; // View es el boton, pero hay que hacer un cast

        if(b.getText().equals("RESET")){
            colorear();
        }else{
            b.setBackgroundColor(Color.WHITE);
        }

    }

    private void colorear() {
        for (int i = 0; i < 18 ; i++) {
            Button b = (Button) gridLayout.getChildAt(i);/// recogemos cada elemento por el indice (botones)
            b.setBackgroundColor(Color.rgb((int) (Math.random()*256),(int) (Math.random()*256), (int) (Math.random()*256))); //255 es el valor maximo
        }
    }
}