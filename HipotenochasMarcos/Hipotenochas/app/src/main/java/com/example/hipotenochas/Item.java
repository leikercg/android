package com.example.hipotenochas;

import android.widget.ImageView;


public class Item {
    private int imageResId;
    private String text;

    public Item(int imageResId, String text) {
        this.imageResId = imageResId;
        this.text = text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getText() {
        return text;
    }
}
