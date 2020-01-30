package com.e.main_hu4_prueba;

import java.util.ArrayList;


public class Conductor {


    String nombre, correo, password, placa;
    ArrayList<Parada> arreglo_paradas;

    public Conductor () {
        this.nombre="";
        this.correo="";
        this.password="";
        this.placa="no";
        this.arreglo_paradas=new ArrayList<Parada>();
    }
    public Conductor(String nombre, String email, String password){
        this.nombre=nombre;
        this.correo=email;
        this.password=password;
        this.placa="no";
        this.arreglo_paradas=new ArrayList<Parada>();

    }
    //

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //

    public String getUsername() {
        return correo;
    }

    public void setUsername(String username) {
        this.correo = username;
    }
    //

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //

    public ArrayList<Parada> getParada() {
        return arreglo_paradas;
    }

    public void setParada(Parada parada) {
        this.arreglo_paradas.add(parada);
    }
    public void setArrayParada(ArrayList<Parada> parada_a) {this.arreglo_paradas=parada_a; }
    //

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return this.placa;
    }
    //

}
