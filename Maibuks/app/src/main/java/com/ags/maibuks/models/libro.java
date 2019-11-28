package com.ags.maibuks.models;

public class libro {

    // Variables de la clase
    private String isbn;
    private String titulo;
    private String fecha;
    private String autor;
    private String idioma;
    private String descripcion;

    // Constructores

    public libro() {
    }

    public libro(String isbn, String titulo, String fecha, String autor, String idioma, String descripcion) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.fecha = fecha;
        this.autor = autor;
        this.idioma = idioma;
        this.descripcion = descripcion;
    }

    // Getters y Setters

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // To String

    @Override
    public String toString() {
        return "libro: " + autor + " - " + titulo;
    }
}
