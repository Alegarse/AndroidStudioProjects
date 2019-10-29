/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet;

// Importaciones necesarias realizadas

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ags.flixnet.modelos.Usuario;
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

public class LoginActivity extends AppCompatActivity
{

    private Button btnLog, btnReg; //Creación de los botonos

    public final int COD_REGISTRO=666; //Establecer código de resgistro

    //
    private FirebaseAuth fbauth ;
    private FirebaseDatabase fbdatabase ;

    private EditText email, pass ;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_login) ;

        // mostramos el icono de la aplicación
        //API<25
        getSupportActionBar().setIcon(R.mipmap.ic_launcher); ;
        // API>=25
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.mipmap.icono);


        // obtenemos una instancia de FirebaseAuth
        fbauth = FirebaseAuth.getInstance() ;

        // obtener una referencia a cada componente
        btnLog = findViewById(R.id.btnLogin) ;
        btnReg = findViewById(R.id.btnRegister) ;
        email  = findViewById(R.id.username) ;
        pass   = findViewById(R.id.password) ;


        // TEMPORAL
        email.setText("aleboy80@gmail.com");
        pass.setText("123456") ;

        //##########################################################################################

        // definimos escuchador para el botón LOGIN
        btnLog.setOnClickListener(new View.OnClickListener()
        {
            /**
             * @param view
             */
            @Override
            public void onClick(View view)
            {
                // guardamos en variables el email y contraseña
                // introducidos por el usuario.
                String ema = email.getText().toString() ;
                String pas = pass.getText().toString() ;

                // logueamos
                fbauth.signInWithEmailAndPassword(ema, pas)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (!task.isSuccessful())
                                    // Error en el Login
                                    Toast.makeText(getApplicationContext(), "Se ha producido un error en el login.", Toast.LENGTH_LONG).show();
                                else
                                { // LOGIN COMPLETADO
                                    // obtener el UID del usuario logueado
                                    String uid = fbauth.getCurrentUser().getUid() ;

                                    // obtener una instancia de FirebaseDatabase
                                    fbdatabase = FirebaseDatabase.getInstance() ;

                                    // obtener una referencia al usuario logueado
                                    DatabaseReference userRef = fbdatabase.getReference().child("usuarios/" + uid) ;
                                    userRef.addListenerForSingleValueEvent(new ValueEventListener()
                                    {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                        {
                                            // comprobar que existe el hijo que busco
                                            if (dataSnapshot.hasChildren())
                                            {
                                                // obtenemos datos del usuario logueado
                                                Usuario usuario = dataSnapshot.getValue(Usuario.class) ;

                                                // creamos un diccionario
                                                Bundle bundle = new Bundle() ;
                                                bundle.putSerializable("_usuario", usuario) ;

                                                // abrimos la actividad principal
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class) ;

                                                // utilizando putExtra puedo almacenar información en la intención
                                                intent.putExtras(bundle) ;
                                                //intent.putExtra("nombre", usuario.getNombre()) ;
                                                //intent.putExtra("apellidos", usuario.getApellidos()) ;

                                                // lanzamos la intención
                                                startActivity(intent) ;
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError)
                                        {

                                        }
                                    }); // end-addListenerForSingleValueEvent

                                }

                            } // end-onComplete

                        }) ; // end-login

                //Snackbar snack ;

                // creamos el snackbar
                //snack = Snackbar.make(view, "Realizando login...", Snackbar.LENGTH_INDEFINITE) ;

                // definir una acción para el snackbar
                //snack.setAction(getResources().getText(R.string.label_ok), new View.OnClickListener()
                //{
                //    @Override
                //    public void onClick(View view) { }
                //}) ;

                //snack.setTextColor(getResources().getColor(R.color.micolor)) ;
                //snack.setBackgroundTint(getResources().getColor(R.color.colorAccent)) ;

                // mostramos el snackbar
                //snack.show() ;
            }
        }) ;
        //##########################################################################################

        // definimos un escuchador para el botón REGISTRO

        btnReg.setOnClickListener(new View.OnClickListener()
        {
            /**
             * @param view
             */
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

                // Empezar la intención
                //startActivity(intent) ;
                startActivityForResult(intent, COD_REGISTRO) ;

            }
        }) ;

    } // fin-onCreate

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        if (requestCode==COD_REGISTRO)
        {
            if (resultCode == RESULT_OK)
                Snackbar.make(btnLog, "El registro ha finalizado con éxito.", Snackbar.LENGTH_LONG).show();
            else
                Snackbar.make(btnLog, "El registro se ha cancelado.", Snackbar.LENGTH_LONG).show();
        }

        //
        super.onActivityResult(requestCode, resultCode, data) ;

    } // fin-onActivityResult
}


