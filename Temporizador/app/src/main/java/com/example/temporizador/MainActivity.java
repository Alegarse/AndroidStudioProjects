package com.example.temporizador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button boton;
    private Intent tempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.boton);

        tempo = new Intent(this, miTempo.class);

        boton.setOnClickListener((v) -> startService(tempo));
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy() ;
        stopService(tempo) ;
    }
}
