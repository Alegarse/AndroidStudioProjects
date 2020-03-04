package com.ivan.procampo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivan.procampo.modelos.Recolectas;

import java.util.UUID;



public class InsertarRecolectaActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    //Definimos los botones y elementos
    private EditText kilos;
    private EditText fecha;
    private EditText dat;
    private EditText matricula;
    private EditText cultivo;

    private Button  boton_aceptar_insertar , boton_cancelar_insertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_recolecta);

        //Inicializamos FireBase
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        boton_aceptar_insertar = findViewById(R.id.boton_aceptar_insertar);
        boton_cancelar_insertar = findViewById(R.id.boton_cancelar_insertar);
        kilos = findViewById(R.id.kilos);
        fecha = findViewById(R.id.fecha);
        dat = findViewById(R.id.dat);
        matricula = findViewById(R.id.matricula);
        cultivo = findViewById(R.id.cultivo);

        boton_cancelar_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volverAMisCultivos = new Intent(InsertarRecolectaActivity.this,MisCultivosActivity.class);
                startActivity(volverAMisCultivos);
            }
        });

        boton_aceptar_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obtener el valor de los campos
                final String losKilos = kilos.getText().toString().trim();
                final String laFecha = fecha.getText().toString().trim();
                final String eldat = dat.getText().toString().trim();
                final String laMatricula = matricula.getText().toString().trim();
                final String elCultivo = cultivo.getText().toString().trim();


                //Comprobamos que los campos tienen informacion
                if (losKilos.isEmpty() || laFecha.isEmpty() || eldat.isEmpty() ||
                        laMatricula.isEmpty() || elCultivo.isEmpty())
                {
                    Snackbar.make(v, getResources().getText(R.string.error_campo_vacio), Snackbar.LENGTH_LONG).show();
                    return ;
                }else{
                    Recolectas rec = new Recolectas();
                    rec.setCodigoRecolecta(UUID.randomUUID().toString());
                    rec.setKilosRecolecta(losKilos);
                    rec.setFechaRecolecta(laFecha);
                    rec.setDATRecolecta(eldat);
                    rec.setMatriculaRecolecta(laMatricula);
                    rec.setCultivoRecolecta(elCultivo);
                    databaseReference.child("RECOLECTAS").child(rec.getCodigoRecolecta()).setValue(rec);
                    //Toast.makeText(this,"Recolecta" ,Toast.LENGTH_LONG).show();
                    Intent volverAMisCultivos = new Intent(InsertarRecolectaActivity.this,MisCultivosActivity.class);
                    startActivity(volverAMisCultivos);

                }
            }
        });
    }
}
