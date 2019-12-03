package com.ags.maipets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ags.maipets.models.Usuario;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase fbdatabase;
    DatabaseReference reference;


    private Button delPerf, editPerf;
    private EditText nombre, apellidos, email, contra;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        //Obtenemos la instancia de FirebaseAuth
        mAuth = FirebaseAuth.getInstance() ;

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance() ;
        String uid = mAuth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference();

        // Instanciamos
        delPerf = findViewById(R.id.delProf);
        editPerf= findViewById(R.id.edProf);
        nombre = findViewById(R.id.nameP);
        apellidos = findViewById(R.id.apeP);
        email = findViewById(R.id.emaP);
        contra = findViewById(R.id.passP);

        // Mostramos los datos

        reference.child("usuarios").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    String nameFb = dataSnapshot.child("nombre").getValue().toString();
                    String apeFb = dataSnapshot.child("apellidos").getValue().toString();
                    String emaFb = dataSnapshot.child("email").getValue().toString();
                    String passFb = dataSnapshot.child("contrasena").getValue().toString();
                    nombre.setText(nameFb);
                    apellidos.setText(apeFb);
                    email.setText(emaFb);
                    contra.setText(passFb);

                }
             }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Boton borrar

        delPerf.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                reference.child("usuarios").child(uid).removeValue();
                reference.child("mascotas").child(uid).removeValue();
                mAuth.signOut();
                Intent salir = new Intent(InfoUser.this,MainActivity.class);
                startActivity(salir);
            }
        });

        // Botón editar

        editPerf.setOnClickListener(new View.OnClickListener() {
            private View v;
            @Override
            public void onClick(View v) {
                final String nom = getField(nombre);
                final String ape = getField(apellidos);
                final String ema = getField(email);
                final String pwd = getField(contra);

                // Verificamos que se han introducido todos los campos
                if (ema.isEmpty() || nom.isEmpty() || ape.isEmpty() ||
                        pwd.isEmpty())
                {
                    Snackbar.make(v, getResources().getText(R.string.e_empty), Snackbar.LENGTH_LONG).show();
                    return ;
                }
                String uid = mAuth.getUid();

                Usuario usuario = new Usuario(nom,ape,ema,pwd);

                DatabaseReference dbref = fbdatabase.getReference("usuarios");

                dbref.child(uid).setValue(usuario) ;
                Snackbar.make(v, getResources().getText(R.string.save_ok), Snackbar.LENGTH_LONG).show();
                finish();
                return;
            }
        });




    }
    // Método para obtener texto de los campos
    private String getField(EditText edit)
    {
        return edit.getText().toString().trim() ;
    }
}
