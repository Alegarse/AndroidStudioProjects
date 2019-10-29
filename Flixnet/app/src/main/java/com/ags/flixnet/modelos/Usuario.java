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
    private String avatar;
    private String nacionalidad;

    // Constructor vacio para FB
    public Usuario() {  }

    public Usuario(String nombre, String apellidos, String avatar, String nacionalidad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.avatar = avatar;
        this.nacionalidad = nacionalidad;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}
