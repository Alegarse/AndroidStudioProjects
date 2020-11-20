package com.example.carplus3g365v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.carplus3g365v2.Modelos.Methods;

public class Listados extends AppCompatActivity {

    public RestClient rest;
    public String respuesta = "";
    private Methods metodo;
    public ProgressDialog miDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listados);

        // instanciamos los Layouts del listado
        ConstraintLayout layoutRecogidas = findViewById(R.id.layout_recogidas);
        ConstraintLayout layoutEntregas = findViewById(R.id.layout_entregas);
        ConstraintLayout layoutAsignados = findViewById(R.id.layout_asignados);
        ConstraintLayout layoutMeeting = findViewById(R.id.layout_meeting);

        // Cargamos las constantes Carplus3G
        Carplus3G constantes = ((Carplus3G) getApplicationContext());
        int entregas = constantes.getPermisoListadoEntrega();
        int recogidas = constantes.getPermisoListadoRecogida();
        int asignados = constantes.getPermisoListadoAsignados();

        // Según las constantes cribamos el listado
        if (entregas == 0) { layoutEntregas.setVisibility(View.GONE); }
        if (recogidas == 0) { layoutRecogidas.setVisibility(View.GONE); }
        if (asignados == 0) { layoutAsignados.setVisibility(View.GONE); }
        layoutMeeting.setVisibility(View.GONE);
        if (constantes.getPermisoRecepcionClientes() == 1) { layoutMeeting.setVisibility(View.VISIBLE); }

        // Anotación del 23-03-2017
        // Listados asignados y meeting no se muestran en esta versión
        layoutAsignados.setVisibility(View.GONE);
        layoutMeeting.setVisibility(View.GONE);

        // Escuchadores para los layouts
        layoutRecogidas.setOnClickListener(view -> btnListadoRecogidasClick());
        layoutEntregas.setOnClickListener(view -> btnListadoEntregasClick());
        layoutAsignados.setOnClickListener(view -> btnListadoAsignadosClick());
        layoutMeeting.setOnClickListener(view -> btnListadoMeetingClick());

        // Instanciamos los botones superiores
        ImageButton menu_ppal = findViewById(R.id.menuPpalL);
        ImageButton salir = findViewById(R.id.salirL);

        metodo = new Methods(Listados.this);

        // Escuchadores para ambos botones
        menu_ppal.setOnClickListener(v -> metodo.iraMenuPpal());
        salir.setOnClickListener(v -> metodo.cerrarSesion());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, R.id.menuMenu, 1, getString(R.string.mnu_ppal)).setIcon(
                R.drawable.menu);

        menu.add(2, R.id.menuLogout,2, getString(R.string.change_user)).setIcon(
                R.drawable.user);
        menu.add(3, R.id.menuSalir, 3, getString(R.string.exit)).setIcon(R.drawable.exit);

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent();
        getApplicationContext();

        switch (item.getItemId()) {

            case R.id.menuSalir:

                System.exit(0);
                break;
            case R.id.menuLogout:

                Carplus3G constantes = ((Carplus3G) getApplicationContext());
                constantes.setIniciadoSesion(false);

                i.setClass(this, Login.class);
                startActivity(i);
                this.finish();
                break;
            case R.id.menuMenu:

                i.setClass(this, MenuPrincipal.class);
                startActivity(i);
                this.finish();
                break;

        }
        return false;
    }

    // Deshabilito el botón de volver atrás
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /* DESACTIVAMOS EL BOTON VOLVER DEL DISPOSITIVO */
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return false;
    }

    // Método para la opción recogidas del listado
    public void btnListadoRecogidasClick() {
        getApplicationContext();
        Intent recogidas = new Intent(this, ListadoRecogidas.class);
        startActivity(recogidas);
        finish();
    }

    // Método para la opción entregas del listado
    public void btnListadoEntregasClick() {
        getApplicationContext();
        Intent entregas = new Intent(this, ListadoEntregas.class);
        startActivity(entregas);
        finish();
    }

    // Método para la opción asignados del listado
    private void btnListadoAsignadosClick() {
        getApplicationContext();
        Intent asignados = new Intent(this,ListadoAsignados.class);
        startActivity(asignados);
        finish();
    }

    // Método para la opción recepción de clientes
    private void btnListadoMeetingClick() {
        getApplicationContext();
        Intent meeting = new Intent(this, ListadoMeeting.class);
        startActivity(meeting);
        finish();
    }
}