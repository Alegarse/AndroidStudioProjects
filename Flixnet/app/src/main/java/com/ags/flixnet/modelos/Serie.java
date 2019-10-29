/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet.modelos;


public class Serie
{

private int idSer ;
private String titulo ;
private String genero ;
private String sinopsis ;
private String cartel ;
private float punt_imdb ;
private String fecha_estreno ;

    /**
     */
    public Serie() { }

    /**
     * @return
     */
    public int getIdSer() {
        return idSer;
    }

    /**
     * @param idSer
     */
    public void setIdSer(int idSer) {
        this.idSer = idSer;
    }

    /**
     * @return
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return
     */
    public String getSinopsis() {
        return sinopsis;
    }

    /**
     * @param sinopsis
     */
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    /**
     * @return
     */
    public String getCartel() {
        return cartel;
    }

    /**
     * @param cartel
     */
    public void setCartel(String cartel) {
        this.cartel = cartel;
    }

    /**
     * @return
     */
    public float getPunt_imdb() {
        return punt_imdb;
    }

    /**
     * @param punt_imdb
     */
    public void setPunt_imdb(float punt_imdb) {
        this.punt_imdb = punt_imdb;
    }

    /**
     * @return
     */
    public String getFecha_estreno() {
        return fecha_estreno;
    }

    /**
     * @param fecha_estreno
     */
    public void setFecha_estreno(String fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }
}
