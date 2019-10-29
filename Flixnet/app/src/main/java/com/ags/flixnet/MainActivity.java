/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.ags.flixnet.api.apiClient;
import com.ags.flixnet.interfaces.ApiService;
import com.ags.flixnet.modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView texto;

    private FirebaseAuth fbauth;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // obtenemos la instancia a FirebaseAuth
        fbauth = FirebaseAuth.getInstance() ;

        // obtenemos la instancia del servicio Retrofit
             apiService = apiClient.getService("https://ajsb.000webhostapp.com/public") ;

        // instanciamos los elementos del layout
        texto = findViewById(R.id.textoPrueba) ;


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

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater() ;
        inflater.inflate(R.menu.menu_principal, menu) ;
        //
        return true ;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mnuLogout :

                // cerramos la sesión en FireBase
                fbauth.signOut() ;

                // cerrar la sesión en la API!!!!!!!
                // ....

                // volvemos a la actividad principal
                setResult(0) ;
                finish() ;

                return true ;
        }

        return super.onOptionsItemSelected(item);
    }
}
