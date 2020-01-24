package com.e.main_hu4_prueba;

public class Conductor {


    String nombre, username, password;
    parada parada=null;

    public Conductor () {
    }
    public Conductor(String nombre, String username, String password){
        this.nombre=nombre;
        this.username=username;
        this.password=password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public parada getParada() {
        return parada;
    }

    public void setParada(parada parada) {
        this.parada = parada;
    }

}