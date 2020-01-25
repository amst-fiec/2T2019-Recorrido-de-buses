package com.e.main_hu4_prueba;

import java.util.ArrayList;

public class Conductor {


    String nombre, correo, password, placa;
    ArrayList<parada> arreglo_paradas;

    public Conductor () {
    }
    public Conductor(String nombre, String email, String password){
        this.nombre=nombre;
        this.correo=email;
        this.password=password;
        this.placa="";
        this.arreglo_paradas=new ArrayList<parada>();

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsername() {
        return correo;
    }

    public void setUsername(String username) {
        this.correo = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<parada> getParada() {
        return arreglo_paradas;
    }

    public void add_Parada(parada parada) {
        this.arreglo_paradas.add(parada);
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
