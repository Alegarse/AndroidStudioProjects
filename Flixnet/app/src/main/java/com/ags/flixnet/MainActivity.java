package com.ags.flixnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnLog, btnReg;
    public final int COD_REGISTRO = 666;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Obtenemos referencia para cada componente
        btnLog = findViewById(R.id.btnLogin);
        btnReg = findViewById(R.id.btnRegister);

        //Definimos escuchador para el boton de Login
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            // Toast con inflador
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
            }
        });

        //Este es para que desde el boton Registro vayamos a su actividad
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, COD_REGISTRO);

            }
        });



    }
}
