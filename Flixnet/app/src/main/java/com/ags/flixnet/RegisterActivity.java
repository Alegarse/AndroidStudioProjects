/**
 * Alejandro García Serrano
 * Proyecto FlixNet de Android Studio
 * Asignatura Programación Multimedia y de Dispositivos Móviles ;Programación de Servicios y Procesos
 *
 */
package com.ags.flixnet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    // Definimos botone a usar
    private Button btnReg, btnCancel;
    public final int COD_REGISTRO = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //P Para guardar datos en caso de reinicio
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // AQUÍ EL CODIGO DE CUANDO PULSAMOS EL BOTON REGISTRAR

        // Instanciamos la vista para el Registro
        btnReg = findViewById(R.id.btnRegister);
        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Se haría todo el proceso de registro

                //Si se realiza con exito regresamos a la pagina principal
                setResult(RESULT_OK); // Mandando el resultado de éxito ok
                finish(); // Terminamos la actividad
                return;  //Sino ponemos esto da fallo
            }
        });

        // AQUÍ EL CODIGO DE CUANDO PULSAMOS EL BOTON CANCELAR REGISTRO

        //Este es para que desde el boton Cancelar vayamos para atrás

        // Instanciamos la vista para la cancelación del registro
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intención para ir de RegisterActivity a MainActivity
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                // Resultado que envio codigo de cancelado
                setResult(RESULT_CANCELED);
                finish(); // Terminamos la actividad
                return; // Hay que ponerlo
            } });

    }

}
