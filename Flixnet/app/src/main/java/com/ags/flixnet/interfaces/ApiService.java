/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet.interfaces;


import com.ags.flixnet.modelos.Capitulo;
import com.ags.flixnet.modelos.Serie;
import com.ags.flixnet.modelos.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiService
{
    @GET("api/series")
    Call<List<Serie>> getAllShows() ;

    @GET("api/serie")
    Call<Serie> getShow(@Query("id") int id) ;

    @GET("api/capitulo")
    Call<Capitulo> getChapter(@Query("id") int id) ;

    /*@POST("api/login")
    @FormUrlEncoded
    Call<Usuario> doLogin(
       @Header("Authorization") String token,
       @Field("email")    String ema,
       @Field("password") String pas
    ) ;*/

}

