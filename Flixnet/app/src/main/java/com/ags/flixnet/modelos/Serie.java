/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet.modelos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Serie
{
    @Expose
    @SerializedName("idSer")
    private int idSer ;

    @Expose
    @SerializedName("titulo")
    private String titulo ;

    @Expose
    @SerializedName("genero")
    private String genero ;

    @Expose
    @SerializedName("sinopsis")
    private String sinopsis ;

    @Expose
    @SerializedName("cartel")
    private String cartel ;

    @Expose
    @SerializedName("punt_imdb")
    private float puntuacion ;

    @Expose
    @SerializedName("fecha_estreno")
    private String estreno ;

    /**
     */
    public Serie() { }

    public Serie(String tit) { titulo = tit ; }

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
     * @param tit
     */
    public void setTitulo(String tit) {
        this.titulo = tit;
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
    public float getPuntuacion() {
        return puntuacion;
    }

    /**
     * @param puntuacion
     */
    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * @return
     */
    public String getEstreno() {
        return estreno;
    }

    /**
     * @param estreno
     */
    public void setEstreno(String estreno) {
        this.estreno = estreno;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Serie: " +  titulo ;
    }
}
