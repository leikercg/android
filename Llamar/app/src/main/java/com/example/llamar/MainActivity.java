package com.example.llamar;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imagen;
    ImageButton boton;
    boolean clicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        imagen=findViewById(R.id.imagen);
        boton= findViewById(R.id.boton);

    }

    public void onClick (View v){
        if(!clicked){
        imagen.setImageResource(R.mipmap.walter_white_sombrero_foreground);
        boton.setImageResource(R.mipmap.colgar);
        clicked=true;
        }else{
            imagen.setImageResource(R.mipmap.walter_white_foreground);
            boton.setImageResource(R.mipmap.llamar);
            clicked=false;
        }
    }
}