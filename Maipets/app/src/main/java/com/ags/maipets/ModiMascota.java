package com.ags.maipets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ModiMascota extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth ;

    // Definimos los elementos de la actividad
    private EditText nombre, especie, raza, color, fecha;
    private Button actualizarCambios, cancelarCambios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_mascota);

        // Inicializamos FireBase
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        // Obtenemos el UID del usuario logueado
        String uid = mAuth.getCurrentUser().getUid();

        // Instanciamos los elementos
        nombre = findViewById(R.id.editName);
        especie = findViewById(R.id.editEspecie);
        raza = findViewById(R.id.editRaza);
        color = findViewById(R.id.editColor);
        fecha = findViewById(R.id.editFecha);
        actualizarCambios = findViewById(R.id.botonActualizar);
        cancelarCambios = findViewById(R.id.botonCancelar);

        // Recogemos los datos que nos llegan
        Bundle extras = getIntent().getExtras();
        String nombreB = extras.getString("nombreMascota");
        String especieB = extras.getString("especieMascota");
        String razaB = extras.getString("razaMascota");
        String colorB = extras.getString("colorMascota");
        String fechaB = extras.getString("fechaMascota");

        // Los asociamos a cada campo de edición
        nombre.setText(nombreB);
        especie.setText(especieB);
        raza.setText(razaB);
        color.setText(colorB);
        fecha.setText(fechaB);


        // Configuramos los botones

        // Boton de cancelar edición
        cancelarCambios.setOnClickListener(v -> {
            Intent cancelado = new Intent(ModiMascota.this, UserActivity.class);
            startActivity(cancelado);
        });

        // Boton de actualizar los datos

        actualizarCambios.setOnClickListener(v -> {
            Map<String, Object> datosAct = new HashMap<>();

            // Recogemos los datos actualizados
            final String nombreAct =  nombre.getText().toString().trim();
            final String especieAct =  especie.getText().toString().trim();
            final String razaAct =  raza.getText().toString().trim();
            final String colorAct =  color.getText().toString().trim();
            final String fechaAct =  fecha.getText().toString().trim();

            // Los agregamos a nuestro HashMap
            datosAct.put("nombre",nombreAct);
            datosAct.put("tipo",especieAct);
            datosAct.put("raza",razaAct);
            datosAct.put("color",colorAct);
            datosAct.put("fechaNac",fechaAct);

            // Actualizamos en Firebase
            databaseReference.child("mascotas").child(uid).child("aUid").updateChildren(datosAct)
                    .addOnSuccessListener((OnSuccessListener)(aVoid) -> {
                Toast.makeText(ModiMascota.this,"Datos de mascota actualizados correctamente",Toast.LENGTH_LONG).show();
                Intent volver = new Intent(ModiMascota.this, UserActivity.class);
                startActivity(volver);
            }).addOnFailureListener((e -> {
                Toast.makeText(ModiMascota.this,"Error al actualizar los datos",Toast.LENGTH_LONG).show();
            }));

        });
    }
}
