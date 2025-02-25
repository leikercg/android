package com.example.biblioteca;

public class Libro {
    int id;
    String categoria;
    String titulo;
    String autor;
    String idioma;
    long fecha_lectura_ini;
    long fecha_lectura_fin;
    String prestado_a;
    float valoracion;
    String formato;
    String notas;

    public Libro(int id, String categoria, String titulo, String autor, String idioma, long fecha_lectura_ini, long fecha_lectura_fin, String prestado_a, float valoracion, String formato, String notas) {
        this.id = id;
        this.categoria = categoria;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.fecha_lectura_ini = fecha_lectura_ini;
        this.fecha_lectura_fin = fecha_lectura_fin;
        this.prestado_a = prestado_a;
        this.valoracion = valoracion;
        this.formato = formato;
        this.notas = notas;
    }

    public Libro(int id, String categoria, String titulo, String autor) {
        this.id = id;
        this.categoria = categoria;
        this.titulo = titulo;
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public long getFecha_lectura_ini() {
        return fecha_lectura_ini;
    }

    public void setFecha_lectura_ini(long fecha_lectura_ini) {
        this.fecha_lectura_ini = fecha_lectura_ini;
    }

    public long getFecha_lectura_fin() {
        return fecha_lectura_fin;
    }

    public void setFecha_lectura_fin(long fecha_lectura_fin) {
        this.fecha_lectura_fin = fecha_lectura_fin;
    }

    public String getPrestado_a() {
        return prestado_a;
    }

    public void setPrestado_a(String prestado_a) {
        this.prestado_a = prestado_a;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
