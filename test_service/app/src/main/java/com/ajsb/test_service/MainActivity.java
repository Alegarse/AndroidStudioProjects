package com.ajsb.test_service;

/**
 * Antonio José Sánchez Bujaldón
 * Programación Multimedia y de Dispositivos Móviles
 * curso 2019/20
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{

    private Intent intent ;
    private Button start, stop ;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start = findViewById(R.id.iniciar) ;
        stop  = findViewById(R.id.detener) ;

        // creo la intención
        intent = new Intent(this, ServicioMusica.class) ;

        startService(intent) ;
        start.setOnClickListener((v) -> startService(intent)) ;
        stop.setOnClickListener ((v) -> stopService(intent)) ;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy() ;
        stopService(intent) ;
    }
}
