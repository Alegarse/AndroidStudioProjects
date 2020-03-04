package com.ivan.procampo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    //Declaracion de variables
    private EditText emailLogin;
    private EditText contraseñaLogin;

    private Button btnAcceder, btnCancelar, btnReset;

    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseDatabase fbdatabase ;
    private final int COD_CANCEL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        fbdatabase =  FirebaseDatabase.getInstance() ;

        //Referenciar los campos y botones
        //Se puede hacer con la herramienta @BindView
        emailLogin = findViewById(R.id.correoLogin);
        contraseñaLogin = findViewById(R.id.contraseñaLogin);

        btnAcceder = findViewById(R.id.botonAccederLogin);
        btnCancelar = findViewById(R.id.botonCancelarLogin);
        btnReset = findViewById(R.id.botonReset);

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Obtener el valor de los campos
                final String email = emailLogin.getText().toString().trim();
                final String contraseña = contraseñaLogin.getText().toString().trim();


                //Comprobamos que los campos tienen informacion
                if (email.isEmpty() || contraseña.isEmpty())
                {
                    Snackbar.make(v, getResources().getText(R.string.error_campo_vacio), Snackbar.LENGTH_LONG).show();


                    return ;
                }

                //Loguear al usuario
                mAuth.signInWithEmailAndPassword(email,contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){//Logueado correctamente

                            Snackbar.make(v, getResources().getText(R.string.login_ok), Snackbar.LENGTH_LONG).show();
                            Toast.makeText(LoginActivity.this, "Logueado correctamente", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();

                            Intent menuppal = new Intent(LoginActivity.this,MisCultivosActivity.class);
                            startActivity(menuppal);

                        }else{
                            //voy a comprobar que no se haga un doble registro
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){

                                Snackbar.make(v, getResources().getText(R.string.doble_usuario_error), Snackbar.LENGTH_LONG).show();
                            }else {
                                //Emitir un error
                                Snackbar.make(v, getResources().getText(R.string.login_error), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volverAInicio = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(volverAInicio);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irAReset = new Intent(LoginActivity.this,ResetPasswordActivity.class);
                startActivity(irAReset);
            }
        });
    }
}
