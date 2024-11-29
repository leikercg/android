package com.example.hipotenochas;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class DifficultyDialog extends Dialog implements View.OnClickListener {
    public DifficultyDialog(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_dialog);

        findViewById(R.id.beginner).setOnClickListener(this);
        findViewById(R.id.amateur).setOnClickListener(this);
        findViewById(R.id.advanced).setOnClickListener(this);
        findViewById(R.id.close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.beginner) {
            MainActivity.setRows(8);
            MainActivity.setColumns(8);
            MainActivity.setHipotenochas(10);
            return;
        }
        if(view.getId()==R.id.amateur){
            MainActivity.setRows(12);
            MainActivity.setColumns(12);
            MainActivity.setHipotenochas(30);
            return;
        }
        if(view.getId()==R.id.advanced){
            MainActivity.setRows(16);
            MainActivity.setColumns(16);
            MainActivity.setHipotenochas(60);
            return;
        }
        if(view.getId()==R.id.close){
            this.dismiss();
        }
    }

}