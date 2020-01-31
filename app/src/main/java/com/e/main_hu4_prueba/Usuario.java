package com.e.main_hu4_prueba;


public class Usuario {
    String nombre, correo, password;

    public Usuario () {
        this.nombre="";
        this.correo="";
        this.password="";

    }
    public Usuario(String nombre, String email, String password){
        this.nombre=nombre;
        this.correo=email;


    }
    //

    public String getNombreU() {
        return nombre;
    }

    public void setNombreU(String nombre) {
        this.nombre = nombre;
    }
    //

    public String getUsernameU() {
        return correo;
    }

    public void setUsernameU(String username) {
        this.correo = username;
    }
    //

    public String getPasswordU() {
        return password;
    }

    public void setPasswordU(String password) {
        this.password = password;
    }
    //


}

