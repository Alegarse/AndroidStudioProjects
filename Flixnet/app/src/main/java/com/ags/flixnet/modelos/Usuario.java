/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet.modelos;


import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String apellidos;
    private String nacionalidad;
    private String email;

    // Constructor vacio para FB
    public Usuario() {  }

    /**
     *
     * @param nombre
     * @param apellidos
     * @param nacionalidad
     */
    public Usuario(String nombre, String apellidos, String email, String nacionalidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
        this.email = email;
    }

    /**
     * @param nombre
     * @param apellidos
     */
    public Usuario(String nombre, String apellidos)
    {
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}
