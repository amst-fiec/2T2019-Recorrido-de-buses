package com.e.main_hu4_prueba;

public class parada {
    public String nombre_parada, coordenadas;
    public float latitud, longitud;

    public parada(String nombre_parada, String coordenadas){
        this.coordenadas=coordenadas;
        this.nombre_parada=nombre_parada;
    }

    public float latitud_f(){
        latitud= Float.parseFloat(coordenadas.split(";")[0]);
        return latitud;
    }

    public float longitud_f(){
        latitud= Float.parseFloat(coordenadas.split(";")[1]);
        return longitud;
    }

    public String getNombre_parada() {
        return nombre_parada;
    }

    public void setNombre_parada(String nombre_parada) {
        this.nombre_parada = nombre_parada;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = coordenadas;
    }
}
