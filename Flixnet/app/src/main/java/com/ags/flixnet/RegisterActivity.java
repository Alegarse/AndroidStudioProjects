/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ags.flixnet.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    //Declaramos las variables con la herramienta BindView

    @BindView(R.id.username)
    public EditText userName;

    @BindView(R.id.apellidos)
    public EditText apellidos;

    @BindView(R.id.email)
    public EditText email;

    @BindView(R.id.password)
    public EditText passWord;

    @BindView(R.id.passwordA)
    public EditText confiPassWord;

    @BindView(R.id.spinner)
    public Spinner pais;

    @BindView(R.id.btnRegister)
    public Button btnOkRegister;

    @BindView(R.id.btnCancel)
    public Button btnCancel;

    //FB
    private FirebaseAuth fbauth ;
    private FirebaseDatabase fbdatabase ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Bindeamos la actividad con ButterKnife
        ButterKnife.bind(this) ;

        //Obtenemos la instancia de FirebaseAuth
        fbauth = FirebaseAuth.getInstance() ;

        //Obtenemos la instancia de FirebaseDatabase
        fbdatabase =  FirebaseDatabase.getInstance() ;

        //#################################################################
        // instanciar la vista
        //email     = findViewById(R.id.regEmail) ;
        //btnReg    = findViewById(R.id.regRegister) ;
        //btnCancel = findViewById(R.id.regCancel) ;

        // añadimos elementos al spinner de manera programática
        // creamos un array con la información que queremos mostrar en
        // el spinner. Podemos utilizar un array o una lista.
        /*String[] paises = new String[] {
                "España", "Italia", "Reino Unido", "Alemania", "Francia"
        } ;*/
        //#################################################################

        List<String> lista = new ArrayList<String>()
        {{
            add("España") ;
            add("Italia") ;
            add("Reino Unido") ;
            add("Alemania") ;
            add("Francia") ;
        }}   ;


        // creamos un adatador para el spinner
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lista) ;

        // asociamos el adaptador al spinner
        pais.setAdapter(adaptador) ;


        //#################################################################################
        /*pais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l)
            {
                Snackbar.make(view, "Seleccionado: " + i, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });*/
        //#################################################################################

        //Escuchador botón cancelar
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cancelamos el proceso y regresamos a la actividad anterior
                setResult(RESULT_CANCELED) ;
                finish() ;

                return ;
            }
        });//Fin escuchador btn Cancel

        //Escuchador botón okRegister
        btnOkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Obtenemos el valor de los campos del formulario

                final String nom = getField(userName) ;
                final String ape = getField(apellidos) ;
                final String ema = getField(email);
                final String pai = pais.getSelectedItem().toString() ;

                String pwd = getField(passWord) ;
                String con = getField(confiPassWord) ;

                // comprobamos si los campos tienen información
                if (ema.isEmpty() || nom.isEmpty() || ape.isEmpty() ||
                        pwd.isEmpty() || con.isEmpty())
                {
                    Snackbar.make(v, getResources().getText(R.string.err_empty), Snackbar.LENGTH_LONG).show();
                    return ;
                }


                // comprobamos si las contraseñas son iguales
                if (!pwd.equals(con))
                {
                    Snackbar.make(v, getResources().getText(R.string.err_pass), Snackbar.LENGTH_LONG).show();
                    return ;
                }

                // registramos en el sistema de autenticación
                fbauth.createUserWithEmailAndPassword(ema,pwd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            /**
                             * @param task
                             */
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    // se ha registrado el usuario correctamente

                                    // guardar la info del usuario en la base de
                                    // datos (API).

                                    // obtenemos UID del usuario registrado
                                    String uid = fbauth.getUid() ;

                                    // en primer lugar, creamos un usuario.
                                    Usuario usuario = new Usuario(ema, nom, ape, pai) ;

                                    // obtener una referencia al documento USUARIOS en FB
                                    DatabaseReference dbref = fbdatabase.getReference("usuarios") ;

                                    // guardamos la información en RealTime Database
                                    dbref.child(uid).setValue(usuario) ;

                                    // ATENCIÓN!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                    // si el registro se produce con éxito, FB
                                    // loguea automáticamente al usuario.

                                    // salimos de la aplicación
                                    fbauth.signOut();

                                    // si hay exito, regresamos a la actividad principal
                                    setResult(RESULT_OK) ;
                                    finish() ;

                                    return ;


                                } else {
                                    // emitimos un error
                                    Snackbar.make(v, getResources().getText(R.string.err_register), Snackbar.LENGTH_LONG).show();
                                }

                            }
                        }) ; // fin-createUserWithEmailAndPassword

            }//fin on click
        });//Fin escuchador btnOkRegister

    }//FIN ON-CREATE

    private String getField(EditText edit)
    {
        return edit.getText().toString().trim() ;
    }
}
