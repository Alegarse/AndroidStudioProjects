package com.alegarse.fichando;

import java.io.Serializable;

public class Usuario implements Serializable {

    // Variales de usuario
    private String nombre;
    private String dni;
    private String email;
    private String contrasena;
    private String uid;

    public Usuario() {
    }

    public Usuario(String nombre, String dni, String email, String contrasena, String uid) {
        this.nombre = nombre;
        this.dni = dni;
        this.email = email;
        this.contrasena = contrasena;
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
