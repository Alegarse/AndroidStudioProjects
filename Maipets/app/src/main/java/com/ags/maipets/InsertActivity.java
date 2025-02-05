/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.maipets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ags.maipets.models.mascota;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class InsertActivity extends AppCompatActivity {

    private FirebaseAuth fbauth ;
    private FirebaseDatabase fbdatabase ;
    public final int COD_REGISTRO=000;

    // Botones y elementos
    private Button cancAdd, okAdd;
    private EditText nombre;
    private EditText tipo;
    private EditText raza;
    private EditText color;
    private EditText fecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        //Obtenemos la instancia de FirebaseAuth
        fbauth = FirebaseAuth.getInstance() ;

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance() ;

        String uid = fbauth.getCurrentUser().getUid();

        // Inicializamos los botones y campos
        cancAdd = findViewById(R.id.cancAddBtn);
        okAdd = findViewById(R.id.addBtn);
        nombre = findViewById(R.id.addName);
        tipo = findViewById(R.id.addType);
        raza = findViewById(R.id.addRace);
        color = findViewById(R.id.addCol);
        fecha = findViewById(R.id.addFec);

        // Escuchador para el botón Cancelar
        cancAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelamos y volvemos al principal
                setResult(RESULT_CANCELED);
                finish();
                return;
            }
        });

        //Escuchador para el boton añadir
        okAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                // Sacamos el valor de los campos
                final String aUid = uid.trim();
                final String aNombre = nombre.getText().toString().trim();
                final String aTipo = tipo.getText().toString().trim();
                final String aRaza = raza.getText().toString().trim();
                final String aColor = color.getText().toString().trim();
                final String aFecha = fecha.getText().toString().trim();

                //Verificamos que los campos contienen información
                if (aNombre.isEmpty() || aTipo.isEmpty() || aRaza.isEmpty() || aColor.isEmpty() || aFecha.isEmpty())
                {
                    Snackbar.make(v, getResources().getText(R.string.e_empty), Snackbar.LENGTH_LONG).show();
                    return ;
                } else {


                    String uid = UUID.randomUUID().toString();
                    mascota masc = new mascota(aNombre,aTipo,aRaza,aColor,aFecha);

                    DatabaseReference dbref = fbdatabase.getReference("mascotas/"+aUid);

                    dbref.child(uid).setValue(masc) ;

                    fbauth.signOut();
                    setResult(RESULT_OK);

                    finish();
                    return;

                }

            }

        });

    }

}
