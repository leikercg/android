package com.example.hipotenochas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CharaCustomAdapter extends ArrayAdapter<String> {
    String[] spinnerNames;
    int[] spinnerImages;
    Context context;

    public CharaCustomAdapter(@NonNull Context context, String[] spinnerNames, int[] spinnerImages) {
        super(context, R.layout.custom_spinner);
        this.spinnerNames = spinnerNames;
        this.spinnerImages = spinnerImages;
        this.context = context;
    }

    @Override
    public int getCount(){
        return spinnerNames.length;
    }

    @NonNull
    @Override
    public View getView(int pos, @Nullable View convertView, @NonNull ViewGroup parent){
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_spinner,parent,false);
            viewHolder.charaName = (TextView) convertView.findViewById(R.id.charaName);
            viewHolder.charaIMG = (ImageView) convertView.findViewById(R.id.charaIMG);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.charaName.setText(spinnerNames[pos]);
        viewHolder.charaIMG.setImageResource(spinnerImages[pos]);

        return convertView;
    }

    @Override
    public View getDropDownView(int pos, @Nullable View convertView, @NonNull ViewGroup parent){
        return getView(pos,convertView,parent);
    }

    private static class ViewHolder{
        ImageView charaIMG;
        TextView charaName;
    }
}

