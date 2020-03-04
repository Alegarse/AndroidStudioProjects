package com.ivan.procampo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ivan.procampo.modelos.Recolectas;
import com.ivan.procampo.modelos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText nomPro, apePro, emaPro, telPro, conPro;

    private Button boton;

    public String userId;
    public String nombreProfile;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    FirebaseAuth auth;

    private TextView muestraNombre;

    private ArrayList<Usuario> mUsuarioList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        muestraNombre = findViewById(R.id.textViewMuestraNombre);

        mDatabase = FirebaseDatabase.getInstance().getReference();




        //Find View By Id
        nomPro = findViewById(R.id.nombreProfile);
        apePro = findViewById(R.id.apellidosProfile);
        //emaPro = findViewById(R.id.emailProfile);
        telPro = findViewById(R.id.telefonoProfile);
        //conPro = findViewById(R.id.contraseñaProfile);



        boton = findViewById(R.id.btnActualizarDatos);

        getPerfilFromFirebase();



        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> usuarioMap = new HashMap<>();
                final String elNombre = nomPro.getText().toString().trim();
                final String losApellidos = apePro.getText().toString().trim();
               // final String elEmail = emaPro.getText().toString().trim();
                final String elTelefono = telPro.getText().toString().trim();
               // final String laContraseña = conPro.getText().toString().trim();

                usuarioMap.put("nombre",elNombre);
                usuarioMap.put("apellidos",losApellidos);
                //usuarioMap.put("email",elEmail);
                usuarioMap.put("telefono",elTelefono);
               // usuarioMap.put("contraseña",laContraseña);
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                mDatabase.child("usuarios").child(userId).updateChildren(usuarioMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ProfileActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

                        Intent irAInicio = new Intent(ProfileActivity.this,MisCultivosActivity.class);
                        startActivity(irAInicio);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Hubo un error al actualizar los datos", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }



    /**
     * Menu Logout
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mnuLogout:{
                Intent irAInicio = new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(irAInicio);
                auth.signOut();
                break;
            }

            case R.id.llamar:{
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:609663323"));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            }

            }



        return false;
    }

    /**
     * Metodo para coger los datos del perfil de firebase
     */

    private void getPerfilFromFirebase(){
        mDatabase.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds : dataSnapshot.getChildren()){
                        String cojoNombre = ds.child("nombre").getValue().toString();
                        String cojoApellidos = ds.child("apellidos").getValue().toString();
                        String cojoTelefono = ds.child("telefono").getValue().toString();




                        nomPro.setText(cojoNombre);
                        apePro.setText(cojoApellidos);
                        telPro.setText(cojoTelefono);
                        muestraNombre.setText(cojoNombre + " " +cojoApellidos);


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
