package com.ags.maipets.models;

import java.io.Serializable;

public class Usuario implements Serializable {

    // Variales de usuario
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasena;

    // Contructor vacío para Firebase
    public Usuario(){}

    // Constructor
    public Usuario(String nombre, String apellidos, String email, String contra) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasena =  contra;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    // ToString

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}
