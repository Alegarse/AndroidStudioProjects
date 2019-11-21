package com.ags.maibuks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ags.maibuks.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterActivity extends AppCompatActivity {


    // Botones y campos del layout
    public Button btnCan, btnReg;
    private EditText nombre, apellidos, pass, conf_pass;
    private EditText email;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fbdatabase ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Instanciamos
        btnCan = findViewById(R.id.regBtnCancel);
        btnReg = findViewById(R.id.regBtnRegister);
        nombre= findViewById(R.id.regName);
        apellidos = findViewById(R.id.regSurname);
        email = findViewById(R.id.regEmail);
        pass = findViewById(R.id.regPassword);
        conf_pass = findViewById(R.id.regPassConfirm);

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance();


        // Escuchador para el botón Cancelar
        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelamos y volvemos al principal
                setResult(RESULT_CANCELED);
                finish();
                return;
            }
        });

        // Escuchador para el botón Registro
        btnReg.setOnClickListener(new View.OnClickListener() {
            private View v;

            @Override
            public void onClick(final View v) {

                // Cogemos el valor de los campos del formulario de registro

                final String nom = getField(nombre);
                final String ape = getField(apellidos);
                final String ema = getField(email);
                final String pwd = getField(pass);
                String cpwd = getField(conf_pass);

                // Verificamos que se han introducido todos los campos
                if (ema.isEmpty() || nom.isEmpty() || ape.isEmpty() ||
                        pwd.isEmpty() || cpwd.isEmpty())
                {
                    Snackbar.make(v, getResources().getText(R.string.e_empty), Snackbar.LENGTH_LONG).show();
                    return ;
                }

                // Verificamos si las contraseñas coinciden
                if (!pwd.equals(cpwd))
                {
                    Snackbar.make(v, getResources().getText(R.string.e_pass), Snackbar.LENGTH_LONG).show();
                    return ;
                }

                // Pasamos a registrar en el sistema de autentificación
                mAuth.createUserWithEmailAndPassword(ema,pwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())  // Usuario se registra correctamente
                                {
                                    // 1.Obtenemos UID del usuario registrado
                                    String uid = mAuth.getUid();

                                    // 2. Creamos el usuario
                                    Usuario usuario = new Usuario(nom,ape,ema,pwd);

                                    // 3. Obtenemos referencia al documento de usuarios en FB
                                    DatabaseReference dbref = fbdatabase.getReference("usuarios");

                                    // 4. Guardamos la información en RealTime Database
                                    dbref.child(uid).setValue(usuario) ;

                                    // 5. Salimos de la aplicación
                                    mAuth.signOut();
                                    setResult(RESULT_OK);
                                    // Aviso de registro por pantalla
                                    Snackbar.make(v, getResources().getText(R.string.ok_register), Snackbar.LENGTH_LONG).show();
                                    finish();
                                    return;

                                } else {  // Usuario no se registra correctamente
                                    // Aviso de error por pantalla
                                    Snackbar.make(v, getResources().getText(R.string.e_register), Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

    }
    // Método para obtener texto de los campos
    private String getField(EditText edit)
    {
        return edit.getText().toString().trim() ;
    }
}
