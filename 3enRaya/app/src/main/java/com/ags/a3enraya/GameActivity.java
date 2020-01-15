package com.ags.a3enraya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GameActivity extends AppCompatActivity {

    private Button reset;
    private int turnos = 0;
    private ImageView celda1,celda2,celda3,celda4,celda5,celda6,celda7,celda8,celda9;

    private boolean player1Turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        reset = findViewById(R.id.reset);
        celda1 = findViewById(R.id.celda1);
        celda2 = findViewById(R.id.celda2);
        celda3 = findViewById(R.id.celda3);
        celda4 = findViewById(R.id.celda4);
        celda5 = findViewById(R.id.celda5);
        celda6 = findViewById(R.id.celda6);
        celda7 = findViewById(R.id.celda7);
        celda8 = findViewById(R.id.celda8);
        celda9 = findViewById(R.id.celda9);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        celda1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda1.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda1.setImageResource(R.drawable.c);
                }
            }
        });

        celda2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda2.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda2.setImageResource(R.drawable.c);
                }
            }
        });

        celda3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda3.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda3.setImageResource(R.drawable.c);
                }
            }
        });

        celda4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda4.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda4.setImageResource(R.drawable.c);
                }
            }
        });

        celda5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda5.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda5.setImageResource(R.drawable.c);
                }
            }
        });

        celda6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda6.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda6.setImageResource(R.drawable.c);
                }
            }
        });

        celda7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda7.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda7.setImageResource(R.drawable.c);
                }
            }
        });

        celda8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda8.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda8.setImageResource(R.drawable.c);
                }
            }
        });

        celda9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player1Turn) {
                    player1Turn = false;
                    turnos++;
                    celda9.setImageResource(R.drawable.x);
                } else {
                    player1Turn = true;
                    turnos++;
                    celda9.setImageResource(R.drawable.c);
                }
            }
        });

        Intent intent = getIntent();
        String Player1 = intent.getStringExtra("nom1");
        String Player2 = intent.getStringExtra("nom2");

        TextView P1 = (TextView) findViewById(R.id.Pl1);
        TextView P2 = (TextView) findViewById(R.id.Pl2);

        P1.setText(Player1);
        P2.setText(Player2);
    }



    private void reset() {

        turnos = 0;
    }


}
