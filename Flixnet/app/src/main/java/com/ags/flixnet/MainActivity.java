/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.ags.flixnet.adaptadores.SeriesAdapter;
import com.ags.flixnet.api.apiClient;
import com.ags.flixnet.interfaces.apiService;
import com.ags.flixnet.modelos.Serie;
import com.ags.flixnet.modelos.Capitulo;
import com.ags.flixnet.modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fbauth ;
    private apiService api ;

    // colección de series de la API
    private List<Serie> series ;
    private SeriesAdapter adapter ;

    @BindView(R.id.mainListShows)
    public RecyclerView recycler ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // asociamos ButterKnife a la actividad
        ButterKnife.bind(this) ;

        // obtenemos la instancia a FirebaseAuth
        fbauth = FirebaseAuth.getInstance() ;

        // obtenemos la instancia del servicio Retrofit de acceso a la API
        api = apiClient.getService("https://ajsb.000webhostapp.com/public/") ;

            /*series = new ArrayList<Serie>()
            {{
            add(new Serie("Narcos")) ;
            add(new Serie("Stranger Things")) ;
            add(new Serie("Elite")) ;
            add(new Serie("Breaking Bad")) ;
            add(new Serie("La casa de papel")) ;
            add(new Serie("Dark")) ;
            add(new Serie("Friends")) ;
            add(new Serie("HIMYM")) ;
            add(new Serie("Bandolero")) ;
            add(new Serie("Doraemon")) ;
            add(new Serie("Vikingos")) ;
            add(new Serie("Juego de Tronos")) ;
            add(new Serie("Heidi")) ;
            add(new Serie("South Park")) ;
            add(new Serie("X Files")) ;
            add(new Serie("Sirens")) ;
            add(new Serie("Mazinger Z")) ;
            add(new Serie("Pokemon")) ;
            add(new Serie("The Simpsons")) ;
            add(new Serie("Black Mirror")) ;
            add(new Serie("La que se avecina")) ;
            add(new Serie("Aquí no hay quien viva")) ;
            add(new Serie("Frasier")) ;
            add(new Serie("Mindhunter")) ;
            add(new Serie("Las chicas del cable")) ;
            }} ;*/

        // creamos el adaptador de SERIES
        //series = new ArrayList<Serie>() ;
        adapter = new SeriesAdapter(this) ;

        recycler.setLayoutManager(new GridLayoutManager(this, 2)) ;
        recycler.setAdapter(adapter) ;

        // obtener listado de series
        getAllShows() ;



        // ##############################################################################
        // obtenemos los EXTRAS almacenados en la intención accediendo,
        // en primer a la intención y seguidamente a la información
        // almacenada.
        // Bundle bundle = getIntent().getExtras() ;
        // Usuario usuario = (Usuario) bundle.getSerializable("_usuario") ;
        //String nombre = bundle.getString("nombre");
        //String apellidos = bundle.getString("apellidos");

        // saludamos al usuario (temporal)
        // texto.setText("Bienvenido/a, " + usuario.getNombre() + " " + usuario.getApellidos()) ;
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

    /**
     */
    private void getAllShows()
    {
        api.getAllShows()
                .enqueue(new Callback<List<Serie>>()
                {
                    /**
                     * @param call
                     * @param response
                     */
                    @Override
                    public void onResponse(Call<List<Serie>> call,
                                           Response<List<Serie>> response)
                    {
                        if (response.isSuccessful())
                        {
                            //Serie serie = response.body() ;
                            //Capitulo capitulo = response.body() ;
                            series = response.body() ;

                            // comunicar al adaptador que tenemos elementos
                            adapter.setLista(series) ;
                        }
                    }

                    /**
                     * @param call
                     * @param t
                     */
                    @Override
                    public void onFailure(Call<List<Serie>> call, Throwable t)
                    {
                        Log.i("FLIXNET", "Respuesta SIN éxito!") ;
                    }
                }) ;
    }

}
