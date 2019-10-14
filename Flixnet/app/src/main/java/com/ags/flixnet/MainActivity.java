/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet;

// Importaciones necesarias realizadas
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    // Creamos las variables a usar en la pantalla inicial
    public final int COD_REGISTRO = 666;
    private Button btnLog, btnReg;

    @Override
    // SavedInstanceState por si reiniciamos que mantega contenido formulario
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Especificamos que es la pantalla inicial

        // Obtenemos referencia para cada componente con findViewById
        btnLog = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegister);

        // Definimos escuchador para el boton de LOGIN
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // View de vista que mostrará al pulsar el boton

                // Puede ser tanto toast como Snackbar, más abajo hay una toast comentada

                // Creamos la Snackbar
                Snackbar snack ;
                snack = Snackbar.make(view, "Realizando login...", Snackbar.LENGTH_INDEFINITE) ;

                // Definimos una acción para la Snackbar
                snack.setAction(getResources().getText(R.string.label_ok), new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view) { }
                }) ;

                // snack.setTextColor(getResources().getColor(R.color.micolor)) ;
                // snack.setBackgroundTint(getResources().getColor(R.color.colorAccent)) ;

                // Mostramos el snackbar
                snack.show() ;

                /*
                Toast con inflador
                LayoutInflater inflater = getLayoutInflater();
                View vista = inflater.inflate(R.layout.toast_layout, null);

                TextView texto;
                texto = vista.findViewById(R.id.toastText);
                texto.setText("Realizando Login.....");

                Toast toast;
                toast = new Toast(getApplicationContext());
                toast.setView(vista);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,650);
                toast.show();
                */
            }
        });

        // Definimos un escuchador para el botón REGISTRO
        // Este es para que desde el boton Registro vayamos a su actividad
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Estamos en MainActivity y vamos hacia RegisterActivity con esta intención
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                startActivityForResult(intent, COD_REGISTRO);
                //startActivity(intent);
            }
        });

    } // Fin de onCreate


    //Ahora sobrecargo método (Combinacion tecla ALT + INSERT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        //Mostramos mensaje cuando la actividad se cierre
        if (requestCode==COD_REGISTRO)
        {
            if (resultCode == RESULT_OK)

                //Toast.makeText(getApplication(),"La actividad de registro se ha cerrado", Toast.LENGTH_SHORT).show();
                //En vez de Toast hacemos SnackBar

                Snackbar.make(btnLog, "El registro ha finalizado con exito", Snackbar.LENGTH_LONG).show();
            else
                Snackbar.make(btnLog, "El registro se ha cancelado", Snackbar.LENGTH_LONG).show();
        }

        // Recopilamos los codigos al resultado de la actividad
        super.onActivityResult(requestCode, resultCode, data);
    } // Final de onActivityResult
}

