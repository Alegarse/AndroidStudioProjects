package com.alegarse.fichando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Inicializamos variables u objetos que intervienen en la actividad
    private EditText email, password;
    private Button login, register;
    private TextView resetPass;

    //Inicilizando para conexión a BBDD (Firebase)
    private FirebaseAuth mAuth ;
    private FirebaseDatabase fbdatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance() ;

        // Referenciamos los campos y botones
        email = findViewById(R.id.userEmailLogin);
        password = findViewById(R.id.userPassLogin);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.btnRegister);
        resetPass = findViewById(R.id.passLoose);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Vamos a verificar si el usuario ha iniciado ya sesión
        checkSession();
    }


    // Método para resetear la contraseña a través de un PopUp
    public void resetear(View view) {
        Intent reset = new Intent(MainActivity.this, PopReset.class);
        startActivity(reset);
    }

    // Método para pasar al registro de usuario en la BBDD (Firebase)
    public void registrar(View view) {
        Intent registrar = new Intent (MainActivity.this, RegActivity.class);
        startActivity(registrar);
    }

    // Método para entrar a la app
    public void logearte(View view) {

        // Guardamos las variables introducidas
        String mail = email.getText().toString();
        String passw = password.getText().toString();

        // Comprobamos que esten rellenos los campos necesarios
        if (mail.isEmpty() || passw.isEmpty() ){
            Snackbar.make(view, getResources().getText(R.string.e_empty), Snackbar.LENGTH_LONG).show();
            return ;
        }



        // Realizamos el logeo con guardado de la sesión
        logIn(mail,passw);
    }

    private void logIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        // Error en el Login
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), R.string.log_nok, Toast.LENGTH_LONG).show();
                        } else
                        {
                            // Login se realiza correctamente
                            // Toast de prueba para si logea bien
                            Toast.makeText(getApplicationContext(), R.string.log_ok, Toast.LENGTH_LONG).show();

                            // 1. Obtenemos el UID del usuario logueado
                            String uid = mAuth.getCurrentUser().getUid();

                            // 2. Obtenemos instancia de FirebaseDatabase
                            fbdatabase = FirebaseDatabase.getInstance();

                            // 3. Obtenemos referencia al usuario logueado
                            DatabaseReference userRef = fbdatabase.getReference().child("CarGestion/Trabajadores/"+uid);

                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    // comprobar que existe el hijo que busco
                                    if (dataSnapshot.hasChildren())
                                    {

                                        // Obtenemos los datos del usuario logeado
                                        Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                        // Metemos los datos necesarios del usuario
                                        Bundle parametros = new Bundle();
                                        parametros.putString("nombre", usuario.getNombre());

                                        //Guardamos sesion de usuario
                                        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                                        sessionManagement.saveSession(usuario);

                                        // Entramos a la app
                                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtras(parametros);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                });
    }

    // Método para verificar si hay ya una sesión iniciada
    private void checkSession() {

        // Verificamos si el usuario está ya logeado
        // Si así fuese entramos directamente a la app

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        String userID = sessionManagement.getSession();

        // Metemos los datos necesarios del usuario
        Bundle parametros = new Bundle();
        parametros.putString("nombre", userID);


        if (userID.equals("Usuario")) {
            // No hacemos nada porque no estaba logeado, y sigue el curso normal de la app
        } else {
            // El usuario ya está logeado
            Intent yaLogeado = new Intent(MainActivity.this, UserActivity.class);
            yaLogeado.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            yaLogeado.putExtras(parametros);
            startActivity(yaLogeado);
            //logIn(mail,pass);
        }
    }
}
