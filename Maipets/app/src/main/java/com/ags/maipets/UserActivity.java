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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;


public class UserActivity extends AppCompatActivity {

    private Button btnAdd;
    private Intent tempo;
    public final int COD_REGISTRO=000;

    private FirebaseAuth fbauth ;
    private FirebaseDatabase fbdatabase;
    DatabaseReference reference,ref;


    // Colección de mascotas
    ArrayList<mascota> mascotas;
    RecyclerView recyclerView;
    MascotaAdapter mascotaAdapter;

    mascota mascotaelegida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btnAdd = findViewById(R.id.addMasc);

        tempo = new Intent(this, miTempo.class);


        //Obtenemos la instancia de FirebaseAuth
        fbauth = FirebaseAuth.getInstance() ;

        String uid = fbauth.getCurrentUser().getUid();



        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance();


        // ZONA CARDVIEW ######################################

        recyclerView = findViewById(R.id.mascShows);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mascotas = new ArrayList<mascota>();


        ref = FirebaseDatabase.getInstance().getReference();
        reference = FirebaseDatabase.getInstance().getReference().child("mascotas").child(uid);

        // El siguiente evento se lanza cada vez que se modifica la base de datos.
        // Si queremos que la consulta se realice UNA ÚNICA VEZ tenemos que utilizar:
        // addListenerForSingleValueEvent
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mascotas.clear() ;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    mascota m = dataSnapshot1.getValue(mascota.class);
                    mascotas.add(m);
                }

                mascotaAdapter = new MascotaAdapter(UserActivity.this,mascotas);
                mascotaAdapter.setMascotas(mascotas) ;
                recyclerView.setAdapter(mascotaAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getApplicationContext(), R.string.no_add, Toast.LENGTH_LONG).show();
            }
        });

        registerForContextMenu(recyclerView);


        // ####################################################


        // Defino escuchador para el botón AÑADIR
        btnAdd.setOnClickListener(viewAdd -> {

            // Intencion para proceder a añadir mascota
            Intent add = new Intent(UserActivity.this, InsertActivity.class);

            // Empezar la intención
            startActivityForResult(add, COD_REGISTRO);
        });



    }

    // Menú de action bar

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
                fbauth.signOut() ;
                // volvemos a la actividad principal
                setResult(0);
                finish();
                return true;

            case R.id.vetInfo:
                Intent vet = new Intent (UserActivity.this, IVetActivity.class);
                startActivity(vet);
                break;

            case R.id.usrInfo:
                Intent info = new Intent(UserActivity.this, InfoUser.class) ;
                startActivity(info);
                break;

            case R.id.mShare:
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(android.content.Intent.EXTRA_TEXT,"https://www.linkedin.com/in/alegarse/");
                startActivity(Intent.createChooser(share,"Compartir via"));
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    // Menú contextual


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secEdd:

                mascota pet = mascotas.get(mascotaAdapter.getIndex());

                Toast.makeText(this, "Editando mascota", Toast.LENGTH_LONG).show();
                Intent irAEditar = new Intent(UserActivity.this, ModiMascota.class);
                irAEditar.putExtra("nombreMascota",pet.getNombre());
                irAEditar.putExtra("especieMascota",pet.getTipo());
                irAEditar.putExtra("razaMascota",pet.getRaza());
                irAEditar.putExtra("colorMascota",pet.getColor());
                irAEditar.putExtra("fechaMascota",pet.getFechaNac());

                startActivity(irAEditar);
                break;

            case R.id.secDel:
                View v;
                String uid = fbauth.getCurrentUser().getUid();
                String UUID = ref.child("mascotas").child(uid).push().getKey();

                ref.child("mascotas").child(uid).child(UUID).removeValue();

                Toast.makeText(this, "Eliminado ", Toast.LENGTH_LONG).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        if (requestCode==COD_REGISTRO)
        {
            if (resultCode == RESULT_OK)
                startService(tempo);
                //Toast.makeText(getApplicationContext(), R.string.ok_addMasc, Toast.LENGTH_LONG).show();
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