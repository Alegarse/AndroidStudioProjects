package com.ivan.procampo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ivan.procampo.modelos.Recolectas;

import java.util.HashMap;
import java.util.Map;

public class ActualizarRecolectaActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    //Definimos los botones y elementos
    private EditText kilos;
    private EditText fecha;
    private EditText dat;
    private EditText matricula;
    private EditText cultivo;
    private TextView codigo;


    private TextView tvDatos;

    private Button boton_aceptar_actualizar , boton_cancelar_actualizar;

    Recolectas recol;

    String codRecolecta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_recolecta);



        //Inicializamos FireBase
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        boton_aceptar_actualizar = findViewById(R.id.boton_aceptar_actualizar);
        boton_cancelar_actualizar = findViewById(R.id.boton_cancelar_actualizar);

        kilos = findViewById(R.id.kilosActualizar);
        fecha = findViewById(R.id.fechaActualizar);
        dat = findViewById(R.id.datActualizar);
        matricula = findViewById(R.id.matriculaActualizar);
        cultivo = findViewById(R.id.cultivoActualizar);
        codigo = findViewById(R.id.codigoRegistro);
////////////////////////////////
        Bundle extras = getIntent().getExtras();
        final String elCodigo = extras.getString("codigoRecolecta");
        String laRecolecta = extras.getString("cultivoRecolecta");
        String eldat = extras.getString("datrecolecta");
        String lafecha = extras.getString("fechaRecolecta");
        String loskilos = extras.getString("kilosRecolecta");
        String lamatricula = extras.getString("matriculaRecolecta");




        codigo.setText(elCodigo);

        cultivo.setText(laRecolecta);
        dat.setText(eldat);
        fecha.setText(lafecha);
        kilos.setText(loskilos);
        matricula.setText(lamatricula);

////////////////////////////////////////
        boton_cancelar_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volverAMisCultivos = new Intent(ActualizarRecolectaActivity.this,MisCultivosActivity.class);
                startActivity(volverAMisCultivos);
            }
        });

        boton_aceptar_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> recolectaMap = new HashMap<>();
                final String actCultivo = cultivo.getText().toString().trim();
                final String actFecha = fecha.getText().toString().trim();
                final String actKilos = kilos.getText().toString().trim();
                final String actDat = dat.getText().toString().trim();
                final String actMatricula = matricula.getText().toString().trim();

                recolectaMap.put("cultivoRecolecta",actCultivo);
                recolectaMap.put("fechaRecolecta",actFecha);
                recolectaMap.put("kilosRecolecta",actKilos);
                recolectaMap.put("datrecolecta",actDat);
                recolectaMap.put("matriculaRecolecta",actMatricula);

                Log.i("TAG",elCodigo);

                databaseReference.child("RECOLECTAS").child(elCodigo).updateChildren(recolectaMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ActualizarRecolectaActivity.this, "Recolecta actualizada correctamente", Toast.LENGTH_SHORT).show();
                        Intent volverAMisCultivos = new Intent(ActualizarRecolectaActivity.this,MisCultivosActivity.class);
                        startActivity(volverAMisCultivos);
                        //limpiarCajas();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActualizarRecolectaActivity.this, "Hubo un error al actualizar la recolecta", Toast.LENGTH_SHORT).show();
                    }
                });




            }
        });




    }


}
