package com.ags.flixnet.api;

import com.ags.flixnet.interfaces.ApiService;
import com.ags.flixnet.interfaces.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiClient {
    //Parser utilizado por retrofit
    private static Gson gson;

    //Intancia de la liberia RETROFIT
    private static Retrofit  retrofit =null;

    //Instancia del servicio (interfaz ApiService)
    private static ApiService service=null;

    //Instancia encargada de realizar las peticiones HTTP
    private static OkHttpClient client;

    /**
     * el constructor debe ser privado cuando usemos SINGLETON
     */
    private apiClient(){}

    /**
     * @param urlBase
     */
    public static ApiService getService (String urlBase){
        if(service==null) {//si es nulo, creamos el servicio

            //Creamos instancia del servicio HTTP
            client = new OkHttpClient.Builder().build();

            //Crear una instancia del parser GSON
            //setlenient permite añadir un grado de indulgencia sobre la sintaxis de JSON, buscando una
            //sokucion aceptable, ante un posible fallo
            gson = new GsonBuilder().setLenient().create();

            //Instanciar la libreria RETROFIT
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlBase)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            //Asociar la instancia de RETROFIT con nuestra interfaz
            service = retrofit.create(ApiService.class);
        }
        return service;
    }

}
