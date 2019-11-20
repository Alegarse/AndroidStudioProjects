package com.ags.maibuks.models;

import java.io.Serializable;

public class Usuario implements Serializable {

    // Variales de usuario
    private String nombre;
    private String apellidos;
    private String email;

    // Contructor vacío para Firebase
    public Usuario (){}

    // Constructor
    public Usuario(String nombre, String apellidos, String email) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
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

    // ToString

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}
