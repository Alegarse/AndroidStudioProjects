/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.maipets;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ags.maipets.adapters.MascotaAdapter;
import com.ags.maipets.models.mascota;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class UserActivity extends AppCompatActivity {

    private Button btnAdd;
    public final int COD_REGISTRO=000;

    private FirebaseAuth mAuth ;
    private FirebaseDatabase fbdatabase;

    // Colección de mascotas
    private List<mascota> mascotas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnAdd = findViewById(R.id.addMasc);

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance();


        // Defino escuchador para el botón AÑADIR
        btnAdd.setOnClickListener(viewAdd -> {

            // Intencion para proceder a añadir mascota
            Intent add = new Intent(UserActivity.this, InsertActivity.class);

            // Empezar la intención
            startActivityForResult(add, COD_REGISTRO);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater() ;
        inflater.inflate(R.menu.menu_user, menu) ;
        //
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mnuLogout :

                // cerramos la sesión en FireBase
                mAuth.signOut() ;
                // volvemos a la actividad principal
                setResult(0) ;
                finish() ;
                return true ;

            case R.id.action_search:

                openSearch();
                return true;

            case R.id.mShare:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(android.content.Intent.EXTRA_TEXT,"https://www.linkedin.com/in/alegarse/");
                startActivity(Intent.createChooser(share,"Compartir via"));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        if (requestCode==COD_REGISTRO)
        {
            if (resultCode == RESULT_OK)
                Toast.makeText(getApplicationContext(), R.string.ok_addMasc, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), R.string.noAddMasc, Toast.LENGTH_LONG).show();
        }

        //
        super.onActivityResult(requestCode, resultCode, data) ;

    }

    private void openSearch(){
        startActivity(new Intent(SearchManager.INTENT_ACTION_GLOBAL_SEARCH));

    }
}