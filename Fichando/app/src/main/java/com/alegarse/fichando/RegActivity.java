package com.alegarse.fichando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegActivity extends AppCompatActivity {

    // Definimos la variables que intervienen
    public Button btnCan, btnReg;
    private EditText nombre, dni, pass, conf_pass, email;
    String uid;

    // Para realizar el registro
    private FirebaseAuth mAuth;
    private FirebaseDatabase fbdatabase ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        // Instaniamos las variables
        btnCan = findViewById(R.id.regBtnCancel);
        btnReg = findViewById(R.id.regBtnRegister);
        nombre= findViewById(R.id.regName);
        dni = findViewById(R.id.regDni);
        email = findViewById(R.id.regEmail);
        pass = findViewById(R.id.regPassword);
        conf_pass = findViewById(R.id.regPassConfirm);

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance();
    }
    // Método para el botón cancelar
    public void cancelar(View view) {
        // Cancelamos y volvemos al principal
        setResult(RESULT_CANCELED);
        finish();
        return;
    }

    // Método para el botón registrar
    public void hacerRegistro(View view) {

        // Cogemos el valor de los campos del formulario de registro
        final String nom = getField(nombre);
        final String ident = getField(dni);
        final String ema = getField(email);
        final String pwd = getField(pass);
        String cpwd = getField(conf_pass);

        // Verificamos que se han introducido todos los campos
        if (ema.isEmpty() || nom.isEmpty() || ident.isEmpty() ||pwd.isEmpty() || cpwd.isEmpty())
        {
            Snackbar.make(view, getResources().getText(R.string.e_empty), Snackbar.LENGTH_LONG).show();
            return ;
        }

        // Verificamos si las contraseñas coinciden
        if (!pwd.equals(cpwd))
        {
            Snackbar.make(view, getResources().getText(R.string.e_pass), Snackbar.LENGTH_LONG).show();
            return ;
        }

        // Pasamos a registrar en el sistema de autentificación
        mAuth.createUserWithEmailAndPassword(ema,pwd)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())  // Usuario se registra correctamente
                    {
                        // 1.Obtenemos UID del usuario registrado
                        uid = mAuth.getUid();

                        // 2. Creamos el usuario
                        Usuario usuario = new Usuario(nom,ident,ema,pwd,uid);

                        // 3. Obtenemos referencia al documento de usuarios en FB
                        DatabaseReference dbref = fbdatabase.getReference("CarGestion/Trabajadores");

                        // 4. Guardamos la información en RealTime Database
                        dbref.child(uid).setValue(usuario) ;

                        // 5. Salimos de la aplicación
                        mAuth.signOut();
                        setResult(RESULT_OK);
                        finish();
                        Intent aLogin = new Intent(RegActivity.this, MainActivity.class);
                        Toast.makeText(getApplicationContext(),getText(R.string.ok_register), Toast.LENGTH_LONG).show();
                        startActivity(aLogin);

                    } else {  // Usuario no se registra correctamente
                        // Aviso de error por pantalla
                        Snackbar.make(view, getResources().getText(R.string.e_register), Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    // Método para obtener texto de los campos
    private String getField(EditText edit)
    {
        return edit.getText().toString().trim() ;
    }
}
