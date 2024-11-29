package com.example.hipotenochas;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


public class SelectCharacterDialog extends Dialog {
    Context context;
    String[] charaNames;
    int[] charaImages;

    Spinner mySpinner;

    public SelectCharacterDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_character_dialog);
        initSpinnerItems();

        mySpinner = findViewById(R.id.characterSpinner);
        CharaCustomAdapter customAdapter = new CharaCustomAdapter(context,charaNames,charaImages);

        mySpinner.setAdapter(customAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.setSelectedCharaImg(charaImages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initSpinnerItems(){
        charaNames = new String[]{"abstracta","afortunada","camuflada","carinosa","che"};
        charaImages = new int[]{R.drawable.abstracta_rescale,R.drawable.afortunada_rescale,R.drawable.camuflada_rescale,R.drawable.carinosa_rescale,R.drawable.che_rescale};
    }
}