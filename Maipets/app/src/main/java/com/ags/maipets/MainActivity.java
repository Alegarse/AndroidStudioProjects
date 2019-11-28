/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.maipets;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

// Importaciones necesarias
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ags.maipets.models.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Botones y campos del layout
    private Button btnLog, btnReg;
    private EditText email, pass ;

    private FirebaseAuth mAuth ;
    private FirebaseDatabase fbdatabase ;


    public final int COD_REGISTRO= 0;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance() ;

        // Referencias a los componentes
        btnLog = findViewById(R.id.btnLogin) ;
        btnReg = findViewById(R.id.btnRegister) ;
        email = findViewById(R.id.userEmailLogin);
        pass = findViewById(R.id.userPassLogin);

        // TEMPORAL PARA LOGEO RAPIDO
        email.setText("aleboy80@gmail.com");
        pass.setText("123456") ;




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

                                                              // Toast de prueba para si logea bien
                                                              Toast.makeText(getApplicationContext(), R.string.log_ok, Toast.LENGTH_LONG).show();

                                                              // 1. Obtenemos el UID del usuario logueado
                                                              String uid = mAuth.getCurrentUser().getUid();

                                                              // 2. Obtenemos instancia de FirebaseDatabase
                                                              fbdatabase = FirebaseDatabase.getInstance();

                                                              // 3. Obtenemos referencia al usuario logueado
                                                              DatabaseReference userRef = fbdatabase.getReference().child("usuarios/"+uid);

                                                              userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                  @Override
                                                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                      // comprobar que existe el hijo que busco
                                                                      if (dataSnapshot.hasChildren())
                                                                      {
                                                                          // Obtenemos los datos del usuario logeado
                                                                          Usuario usuario = dataSnapshot.getValue(Usuario.class) ;

                                                                          // creamos un diccionario para poner los datos del usuario
                                                                          Bundle bundle = new Bundle() ;
                                                                          bundle.putSerializable("_usuario", usuario) ;

                                                                          // Pasamos a la actividad de usuario
                                                                          Intent intent = new Intent(MainActivity.this, UserActivity.class) ;

                                                                          // Almacenamos información en la intencion
                                                                          intent.putExtras(bundle) ;

                                                                          // Lanzamos la intención
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
                startActivityForResult(register, COD_REGISTRO) ;

            }
        });

        // ###############################   VOLVEMOS DEL REGISTRO    ##############################

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        if (requestCode==COD_REGISTRO)
        {
            if (resultCode == RESULT_OK)
                Toast.makeText(getApplicationContext(), R.string.ok_register, Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), R.string.noReg, Toast.LENGTH_LONG).show();
        }

        //
        super.onActivityResult(requestCode, resultCode, data) ;

    } // fin-onActivityResult

}
