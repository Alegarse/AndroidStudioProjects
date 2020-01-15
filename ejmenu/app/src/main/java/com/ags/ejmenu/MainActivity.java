package com.ags.ejmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /* Para meter el menú en la pantalla lo inflamos*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.main_menu, menu) ;

        //
        return true; // Hemos creado el menú correctamente
    }

    /* Para cuando se usa una de lasopciones del menu*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {


        View vista = getWindow().getDecorView().getRootView();
        switch (item.getItemId())

    {
        case R.id.mnu_add:
            Snackbar.make(vista, "Añadir entrada", Snackbar.LENGTH_LONG).show();
            break;

        case R.id.mnu_del:
            Snackbar.make(vista, "Borrar entrada", Snackbar.LENGTH_LONG).show();
            break;

        case R.id.mnu_logout:
            Snackbar.make(vista, "Bye bye!!", Snackbar.LENGTH_LONG).show();
            break;
    }

        return super.onOptionsItemSelected(item);
    }
}
