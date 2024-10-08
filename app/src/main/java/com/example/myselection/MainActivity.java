package com.example.myselection;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    CheckBox checkBox;
    TextView tv;
    RadioButton rb1, rb2;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        checkBox=findViewById(R.id.checkBox);
        tv = findViewById(R.id.textView);
        rb1= findViewById(R.id.radioButton);
        rb2= findViewById(R.id.radioButton2);
        rg=findViewById(R.id.radioGroup);

        //Agregar listener al checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    compoundButton.setText("!CheckBox Marcado");
                }
                else{
                    compoundButton.setText("!CheckBox DESmarcado");
                }
            }
        });

        //Agregar listener al radio group
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {//boton pulsado del grupo
                //tv.setText("ID opción selecionada: "+ i);
                if (rb1.isChecked()){
                    rb1.setText("Opción 1, seleccionada");
                    rb2.setText("Opción 2");

                }else{
                    rb1.setText("Opción 1");
                    rb2.setText("Opción 2, seleccionada");

                }
            }
        });


    }
}