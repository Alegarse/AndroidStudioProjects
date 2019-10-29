/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ags.flixnet.api.apiClient;
import com.ags.flixnet.interfaces.ApiService;
import com.ags.flixnet.modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView texto;
    private FirebaseAuth fbauth;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbauth = FirebaseAuth.getInstance();

        api = apiClient.getService("https://ajsb.000webhostapp.com/public/");

        api.getShow(2);

        /**
         * API, CODIGO 28/10/2019
         */

        // Instanciamos los elementos del layout
        //texto = findViewById(R.id.textoPrueba) ;

        // obtenemos los EXTRAS almacenados en la intención accediendo,
        // en primer a la intención y seguidamente a la información
        // almacenada.
        Bundle bundle = getIntent().getExtras() ;
        Usuario usuario = (Usuario) bundle.getSerializable("_usuario") ;
        //String nombre = bundle.getString("nombre");
        //String apellidos = bundle.getString("apellidos");

        // saludamos al usuario (temporal)
        texto.setText("Bienvenido/a, " + usuario.getNombre() + " " + usuario.getApellidos()) ;
    }
}
