package com.example.listview;

public class Titular {
    String titulo;
    String subtitulo;

    public Titular(String titulo, String subtitulo) {
        this.subtitulo = subtitulo;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

}
