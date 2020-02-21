package com.ajsb.a3enraya;

/**
 * Antonio José Sánchez Bujaldón
 * Programación Multimedia y de dispositivos Móviles
 * curso 2019/20
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.email)
    public EditText ema ;

    @BindView(R.id.password)
    public EditText pwd ;

    //
    public Button btn ;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.jugar) ;
        btn.setOnClickListener((view) ->
        {
            Intent i = new Intent(MainActivity.this, GameActivity.class) ;
            startActivity(i) ;
        }) ;

    }
}
