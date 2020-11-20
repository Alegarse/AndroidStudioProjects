package com.example.carplus3g365v2.Modelos;

public class Service {
    private String titulo;
    private String descripcion;
    private int imagenServicio;

    public Service(String titulo, String descripcion, int imagenServicio) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenServicio = imagenServicio;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getImagenServicio() {
        return imagenServicio;
    }

    public void setImagenServicio(int imagenServicio) {
        this.imagenServicio = imagenServicio;
    }
}


