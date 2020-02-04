package com.e.main_hu4_prueba;

import java.util.ArrayList;


public class Conductor {


    String nombre, correo, password, placa, nombre_ruta;
    ArrayList<Parada> arreglo_paradas;
    //nuevas variables
    int capacidad_max_bus, edad;

    public Conductor () {
        this.nombre="";
        this.correo="";
        this.password="";
        this.placa="no";
        this.arreglo_paradas=new ArrayList<Parada>();
        //nuevo
        this.capacidad_max_bus= 0;
        this.edad=0;
        this.nombre_ruta="no";

    }

    public Conductor(String nombre, String email, String password, String nombre_ruta, int capacidad_max_bus, int edad){
        this.nombre=nombre;
        this.correo=email;
        this.password=password;
        this.placa="no";
        this.arreglo_paradas=new ArrayList<Parada>();
        //nuevo
        this.capacidad_max_bus= capacidad_max_bus;
        this.edad=edad;
        this.nombre_ruta= nombre_ruta;
    }

    public Conductor(String nombre, String email, String password){
        this.nombre=nombre;
        this.correo=email;
        this.password=password;
        this.placa="no";
        this.arreglo_paradas=new ArrayList<Parada>();
        //nuevo
        this.capacidad_max_bus= 0;
        this.edad=0;
        this.nombre_ruta= "no";
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

    public int getCapacidad_max_bus() { return capacidad_max_bus; }

    public void setCapacidad_max_bus(int capacidad_max_bus) { this.capacidad_max_bus = capacidad_max_bus; }
    //

    public int getEdad() { return edad; }

    public void setEdad(int edad) { this.edad = edad; }
    //


    public String getNombre_ruta() { return nombre_ruta; }

    public void setNombre_ruta(String nombre_ruta) { this.nombre_ruta = nombre_ruta;
    }
}
