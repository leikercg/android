package com.example.sorpresa;

public class Estacion {
    String nombre;
    String cordillera;
    int n_remontes;
    float km_pistas;
    long fecha_ult_visita;
    float valoracion;
    String notas;

    public Estacion(String nombre, String cordillera, int n_remontes, float km_pistas, long fecha_ult_visita, float valoracion, String notas) {

        this.nombre = nombre;
        this.cordillera= cordillera;
        this.n_remontes = n_remontes;
        this.km_pistas = km_pistas;
        this.fecha_ult_visita = fecha_ult_visita;
        this.valoracion = valoracion;
        this.notas = notas;
    }

    public long getFecha_ult_visita() {
        return fecha_ult_visita;
    }

    public void setFecha_ult_visita(long fecha_ult_visita) {
        this.fecha_ult_visita = fecha_ult_visita;
    }

    public String getCordillera() {
        return cordillera;
    }

    public void setCordillera(String cordillera) {
        this.cordillera = cordillera;
    }

    public float getKm_pistas() {
        return km_pistas;
    }

    public void setKm_pistas(float km_pistas) {
        this.km_pistas = km_pistas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getN_remontes() {
        return n_remontes;
    }

    public void setN_remontes(int n_remontes) {
        this.n_remontes = n_remontes;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }
}
