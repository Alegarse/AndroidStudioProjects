package com.ivan.procampo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivan.procampo.adaptadores.RecolectaAdapter;
import com.ivan.procampo.modelos.Recolectas;

import java.util.ArrayList;

public class MisCultivosActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference mDatabase;

    private ActionBar toolbar;

    private BottomNavigationView bottomNavigationView;

    private RecolectaAdapter mAdapter;

    private RecyclerView mRecyclerView;

    private ArrayList<Recolectas> mRecolectasList = new ArrayList<>();

    Recolectas recolectaSelected;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_cultivos);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewRecolectas);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));




        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
       // bottomNavigationView.setSelectedItemId(R.id.añadirRecolecta);

        getRecolectasFromFirebase();

        registerForContextMenu(mRecyclerView);





    }

    /**
     * Metodo creado para coger los datos de Firebase
     *
     */

    private void getRecolectasFromFirebase(){
        mDatabase.child("RECOLECTAS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        String cultivo = ds.child("cultivoRecolecta").getValue().toString();
                        String codigo = ds.child("codigoRecolecta").getValue().toString();
                        String fecha = ds.child("fechaRecolecta").getValue().toString();
                        String kilos =  ds.child("kilosRecolecta").getValue().toString();
                        String dat = ds.child("datrecolecta").getValue().toString();
                        String matricula = ds.child("matriculaRecolecta").getValue().toString();



                        mRecolectasList.add(new Recolectas(cultivo,codigo,fecha,kilos,dat,matricula));
                    }

                    mAdapter = new RecolectaAdapter(mRecolectasList,R.layout.recolecta_view);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    /**
     * Creado para el implements
     * @param menuItem
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.añadirRecolecta:{
                Intent irAInsertar = new Intent(MisCultivosActivity.this,InsertarRecolectaActivity.class);
                startActivity(irAInsertar);
                break;

            }

            case R.id.navigation_fertilizantes:{
                Intent irAFertilizantes = new Intent(MisCultivosActivity.this,FertilizantesActivity.class);
                startActivity(irAFertilizantes);
                break;
            }

            case R.id.navigation_perfil:{
                Intent irAlPerfil = new Intent(MisCultivosActivity.this,ProfileActivity.class);
                startActivity(irAlPerfil);
                break;

            }
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual, menu);
    }



    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.ctxMod:



                Recolectas recolecta = mRecolectasList.get(mAdapter.getIndex());

                Toast.makeText(this, "Modificar cultivo seleccionado " , Toast.LENGTH_LONG).show();

                Intent irAEditar = new Intent(MisCultivosActivity.this,ActualizarRecolectaActivity.class);

                irAEditar.putExtra("cultivoRecolecta", recolecta.getCultivoRecolecta());
                irAEditar.putExtra("codigoRecolecta",recolecta.getCodigoRecolecta());
                irAEditar.putExtra("datrecolecta", recolecta.getDATRecolecta());
                irAEditar.putExtra("fechaRecolecta", recolecta.getFechaRecolecta());
                irAEditar.putExtra("kilosRecolecta", recolecta.getKilosRecolecta());
                irAEditar.putExtra("matriculaRecolecta", recolecta.getMatriculaRecolecta());

                startActivity(irAEditar);




                break;
            case R.id.ctxDel:

                Recolectas recolectas = mRecolectasList.get(mAdapter.getIndex());
                String cultivo = recolectas.getCultivoRecolecta();

                AlertDialog.Builder myBuild = new AlertDialog.Builder(this);
                myBuild.setTitle("CONFIRMACIÓN DE BORRADO");
                myBuild.setMessage("¿Quiere eliminar la recolecta al cultivo '"+cultivo +"' ?");
                myBuild.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Recolectas recolectas = mRecolectasList.get(mAdapter.getIndex());
                        String codigo = recolectas.getCodigoRecolecta();

                        mDatabase.child("RECOLECTAS").child(codigo).removeValue();

                        //Toast.makeText(this, "Eliminado " , Toast.LENGTH_LONG).show();

                        Intent refresh = new Intent(MisCultivosActivity.this, MisCultivosActivity.class);
                        startActivity(refresh);

                    }
                });

                myBuild.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog dialog = myBuild.create();
                dialog.show();

                break;
        }
        return super.onContextItemSelected(item);
    }








}

