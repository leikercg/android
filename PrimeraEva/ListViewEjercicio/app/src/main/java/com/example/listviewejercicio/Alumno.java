package com.example.listviewejercicio;

import java.io.Serializable;

public class Alumno implements Serializable { // para poder pasar el objeto a la otra actividad
    String nombre;
    float nota;

    public Alumno(String nombre, float nota)  {
        this.nota = nota;
        this.nombre = nombre;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
