package com.ags.flixnet.modelos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Capitulo {
    @Expose
    @SerializedName("idCap")
    private int idCap ;

    @Expose
    @SerializedName("temporada")
    private int temporada ;

    @Expose
    @SerializedName("capitulo")
    private int capitulo ;

    @Expose
    @SerializedName("titulo")
    private String titulo ;

    @Expose
    @SerializedName("sinopsis")
    private String sinopsis ;

    @Expose
    @SerializedName("puntuacion")
    private float puntuacion ;

    /**
     */
    public Capitulo() { }

    public int getIdCap() {
        return idCap;
    }

    public void setIdCap(int idCap) {
        this.idCap = idCap;
    }

    public int getTemporada() {
        return temporada;
    }

    public void setTemporada(int temporada) {
        this.temporada = temporada;
    }

    public int getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(int capitulo) {
        this.capitulo = capitulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Capitulo: " + titulo ;
    }

}
