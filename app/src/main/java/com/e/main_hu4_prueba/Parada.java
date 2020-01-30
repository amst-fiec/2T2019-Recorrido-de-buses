package com.e.main_hu4_prueba;

public class Parada {
    public String nombre_parada, coordenadas1,coordenadas2;
    //public float latitud, longitud;

    public Parada(String nombre_parada, String coordenadas1, String coordenadas2){
        this.coordenadas1=coordenadas1;
        this.coordenadas2=coordenadas2;
        this.nombre_parada=nombre_parada;
    }

    public Parada(){
        this.coordenadas1="";
        this.coordenadas2="";
        this.nombre_parada="";
    }

    /*public float latitud_f(){
        latitud= Float.parseFloat(coordenadas1);
        return latitud;
    }

    public float longitud_f(){
        latitud= Float.parseFloat(coordenadas2);
        return longitud;
    }

    public String getNombre_parada() {
        return nombre_parada;
    }
    */
    public String getNombre_parada() { return this.nombre_parada;    }

    public void setNombre_parada(String nombre_parada) {
        this.nombre_parada = nombre_parada;
    }

    public String getCoordenadas1() {
        return coordenadas1;
    }

    public void setCoordenadas1(String coordenadas1) {
        this.coordenadas1 = coordenadas1;
    }

    public String getCoordenadas2() {
        return coordenadas2;
    }

    public void setCoordenadas2(String coordenadas2) {
        this.coordenadas2 = coordenadas2;
    }
}