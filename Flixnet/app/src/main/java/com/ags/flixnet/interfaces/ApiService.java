/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet.interfaces;


import com.ags.flixnet.modelos.Capitulo;
import com.ags.flixnet.modelos.Serie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService
{
    //Que tipo de protocolo y su estructura
    @GET("api/serie")
    //Complementa el id con el idSer
    Call<Serie> getAllShows(@Query("id") int id);

    @GET("api/serie")
    Call<Serie> getShow(@Query("id") int id);

    @GET("api/capitulo")
    Call<Capitulo>getChapter(@Query("id")int id);

    //@POST("api/login")
    //Call<Usuario> doLogin();

}
