package com.ags.flixnet.api;

import android.util.Log;

import com.ags.flixnet.interfaces.apiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiClient
{
    // parser utilizado por RETROFIT
    private static Gson gson ;

    // instancia de la librería RETROFIT
    private static Retrofit retrofit = null ;

    // instancia del servicio (interfaz apiService)
    private static apiService service = null ;

    // instancia encargada de realizar las peticiones HTTP
    private static OkHttpClient client ;

    // se encargará de interceptar los paquetes HTTP intercambiados entre
    // RETROFIT y la API.
    private static HttpLoggingInterceptor interceptor ;

    /**
     * el constructor debe ser privado cuando usamos SINGLETON
     */
    private apiClient() { }

    /**
     */
    public static apiService getService(String urlBase)
    {
        if (service == null) {

            // instanciar el interceptor
            interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NotNull String respuesta)
                {
                    Log.d("INTERCEPTOR:", respuesta) ;
                }
            }) ;

            // definimos a qué nivel deseamos realizar la intercepción
            interceptor.level(HttpLoggingInterceptor.Level.BODY) ;

            // creamos instancia del servicio HTTP
            client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();

            // crear una instancia del parser GSON
            // setLenient permite añadir un grado de indulgencia sobre la sintaxis
            // de JSON, proporcionando una solución aceptable ante un posible fallo.
            gson = new GsonBuilder().setLenient().create();

            // instanciar la librería RETROFIT
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlBase)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            // asociar la instancia de RETROFIT con nuestra interfaz
            service = retrofit.create(apiService.class);
        }

        //
        return service ;
    }

}

