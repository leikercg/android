package com.example.bdrecycle;

public class DatosPersonales
{
    String nombre;
    int edad;

    public DatosPersonales(String nombre,int edad) {

        this.nombre = nombre;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
