package com.ags.maipets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ags.maipets.models.veterinario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class IVetActivity extends AppCompatActivity {

    private FirebaseAuth fbauth ;
    private FirebaseDatabase fbdatabase ;
    public final int COD_REGISTRO=000;

    // Botones y elementos
    private Button cancAdd, okAdd;
    private EditText no;
    private EditText di;
    private EditText ci;
    private EditText te;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ivet);

        //Obtenemos la instancia de FirebaseAuth
        fbauth = FirebaseAuth.getInstance() ;

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance() ;
        String uid = fbauth.getCurrentUser().getUid();

        cancAdd = findViewById(R.id.cancAddBtnV);
        okAdd = findViewById(R.id.addBtnV);
        no = findViewById(R.id.nameVet);
        di = findViewById(R.id.dirVet);
        ci = findViewById(R.id.ciuVet);
        te = findViewById(R.id.telVet);

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
                final String aNombre = no.getText().toString().trim();
                final String aDire = di.getText().toString().trim();
                final String aCiudad = ci.getText().toString().trim();
                final String aTel = te.getText().toString().trim();

                //Verificamos que los campos contienen información
                if (aNombre.isEmpty() || aDire.isEmpty() || aCiudad.isEmpty() || aTel.isEmpty())
                {
                    Snackbar.make(v, getResources().getText(R.string.e_empty), Snackbar.LENGTH_LONG).show();
                    return ;
                } else {


                    String uid = UUID.randomUUID().toString();
                    veterinario vet = new veterinario(aNombre,aDire,aCiudad,aTel);

                    DatabaseReference dbrefe = fbdatabase.getReference("veterinarios/"+aUid);

                    dbrefe.child(uid).setValue(vet) ;

                    fbauth.signOut();
                    setResult(RESULT_OK);
                    finish();
                    return;

                }

            }

        });

    }
}
