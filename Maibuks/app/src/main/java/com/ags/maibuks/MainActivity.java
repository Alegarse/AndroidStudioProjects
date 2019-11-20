/**
 * Alejandro García Serrano
 * Proyecto de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.maibuks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

// Importaciones necesarias
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    // Botones y campos del layout
    private Button btnLog, btnReg;
    private EditText email, pass ;

    private FirebaseAuth mAuth ;
    private FirebaseDatabase fbdatabase ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias a los componentes
        btnLog = findViewById(R.id.btnLogin) ;
        btnReg = findViewById(R.id.btnRegister) ;
        email = findViewById(R.id.userEmailLogin);
        pass = findViewById(R.id.userPassLogin);


        // ################################   LOGIN    #############################################

        // Defino escuchador para el botón LOGIN
        btnLog.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View viewLog) {

                // Guardamos las variables introducidas
                String mail = email.getText().toString();
                String passw = pass.getText().toString();

                // Logueamos
                mAuth.signInWithEmailAndPassword(mail,passw)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (!task.isSuccessful()){ // Error en el Login
                                    Toast.makeText(getApplicationContext(), R.string.log_nok, Toast.LENGTH_LONG).show();
                                } else { // Login correcto
                                    // 1. Obtenemos el UID del usuario logueado
                                    String uid = mAuth.getCurrentUser().getUid();

                                    // 2. Obtenemos instancia de FirebaseDatabase
                                    fbdatabase = FirebaseDatabase.getInstance();

                                    // 3. Obtenemos referencia al usuario logueado
                                    DatabaseReference userRef = fbdatabase.getReference().child("usuarios/"+uid);
                                }

                            }
                        });

            }
        }
        );

        // ################################   REGISTRO    ##########################################

        // Defino escuchador para el botón REGISTRO
        btnReg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View viewReg) {

                // Intencion para proceder al registro de usuario
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);

                // Empezar la intención
                startActivity(register) ;

            }
        });
    }
}
