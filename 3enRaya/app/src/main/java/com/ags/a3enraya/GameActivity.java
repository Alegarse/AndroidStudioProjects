package com.ags.a3enraya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends AppCompatActivity {

    private Button reset;
    private int turnos = 0;
    private ImageView celda1,celda2,celda3,celda4,celda5,celda6,celda7,celda8,celda9;
    private String c1, c2, c3, c4, c5, c6, c7, c8 ,c9;
    private boolean player1Turn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        reset  = findViewById(R.id.reset);
        celda1 = findViewById(R.id.celda1);
        celda2 = findViewById(R.id.celda2);
        celda3 = findViewById(R.id.celda3);
        celda4 = findViewById(R.id.celda4);
        celda5 = findViewById(R.id.celda5);
        celda6 = findViewById(R.id.celda6);
        celda7 = findViewById(R.id.celda7);
        celda8 = findViewById(R.id.celda8);
        celda9 = findViewById(R.id.celda9);

        Intent intent = getIntent();
        String Player1 = intent.getStringExtra("nom1");
        String Player2 = intent.getStringExtra("nom2");

        TextView P1 = findViewById(R.id.Pl1);
        TextView P2 = findViewById(R.id.Pl2);

        P1.setText(Player1);
        P2.setText(Player2);

        /* INDICA QUE COMIENZA ESTE JUGADOR AL RESALTARLO */
        P1.setTextColor(Color.YELLOW);

        /* ESCUCHADOR BOTON RESET */
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        /* ESCUCHADORES PARA CADA UNA DE LAS CELDAS */

        celda1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda1.setImageResource(R.drawable.x);
                    c1 = "X";
                } else {
                    turnos++;
                    celda1.setImageResource(R.drawable.c);
                    c1 = "C";
                }
                player1Turn = !player1Turn;
                celda1.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda2.setImageResource(R.drawable.x);
                    c2 = "X";
                } else {
                    turnos++;
                    celda2.setImageResource(R.drawable.c);
                    c2 = "C";
                }
                player1Turn = !player1Turn;
                celda2.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda3.setImageResource(R.drawable.x);
                    c3 = "X";
                } else {
                    turnos++;
                    celda3.setImageResource(R.drawable.c);
                    c3 = "C";
                }
                player1Turn = !player1Turn;
                celda3.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda4.setImageResource(R.drawable.x);
                    c4 = "X";
                } else {
                    turnos++;
                    celda4.setImageResource(R.drawable.c);
                    c4 = "C";
                }
                player1Turn = !player1Turn;
                celda4.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda5.setImageResource(R.drawable.x);
                    c5 = "X";
                } else {
                    turnos++;
                    celda5.setImageResource(R.drawable.c);
                    c5 = "C";
                }
                player1Turn = !player1Turn;
                celda5.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda6.setImageResource(R.drawable.x);
                    c6 = "X";
                } else {
                    turnos++;
                    celda6.setImageResource(R.drawable.c);
                    c6 = "C";
                }
                player1Turn = !player1Turn;
                celda6.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda7.setImageResource(R.drawable.x);
                    c7 = "X";
                } else {
                    turnos++;
                    celda7.setImageResource(R.drawable.c);
                    c7 = "C";
                }
                player1Turn = !player1Turn;
                celda7.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda8.setImageResource(R.drawable.x);
                    c8 ="X";
                } else {
                    turnos++;
                    celda8.setImageResource(R.drawable.c);
                    c8 = "C";
                }
                player1Turn = !player1Turn;
                celda8.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

        celda9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player1Turn) {
                    turnos++;
                    celda9.setImageResource(R.drawable.x);
                    c9 = "X";
                } else {
                    turnos++;
                    celda9.setImageResource(R.drawable.c);
                    c9 = "C";
                }
                player1Turn = !player1Turn;
                celda9.setEnabled(false);
                color();
                if (checkWin()) {
                    funcion();
                } else {
                    funcion2();
                }
            }
        });

    }

    private void reset() {

        celda1.setImageResource(R.drawable.v);
        celda1.setEnabled(true);
        c1 = "";
        celda2.setImageResource(R.drawable.v);
        celda2.setEnabled(true);
        c2 = "";
        celda3.setImageResource(R.drawable.v);
        celda3.setEnabled(true);
        c3 = "";
        celda4.setImageResource(R.drawable.v);
        celda4.setEnabled(true);
        c4 = "";
        celda5.setImageResource(R.drawable.v);
        celda5.setEnabled(true);
        c5 = "";
        celda6.setImageResource(R.drawable.v);
        celda6.setEnabled(true);
        c6 = "";
        celda7.setImageResource(R.drawable.v);
        celda7.setEnabled(true);
        c7 = "";
        celda8.setImageResource(R.drawable.v);
        celda8.setEnabled(true);
        c8 = "";
        celda9.setImageResource(R.drawable.v);
        celda9.setEnabled(true);
        c9 = "";

        player1Turn = true;
        turnos = 0;
    }

    private boolean checkWin() {

        /* Checking circle */
        if (c1 == "C" && c2 == "C" && c3 == "C"){ return true; }
        if (c4 == "C" && c5 == "C" && c6 == "C"){ return true; }
        if (c7 == "C" && c8 == "C" && c9 == "C"){ return true; }
        if (c1 == "C" && c4 == "C" && c7 == "C"){ return true; }
        if (c2 == "C" && c5 == "C" && c8 == "C"){ return true; }
        if (c3 == "C" && c6 == "C" && c9 == "C"){ return true; }
        if (c1 == "C" && c5 == "C" && c9 == "C"){ return true; }
        if (c3 == "C" && c5 == "C" && c7 == "C"){ return true; }


        /* Checking equis */
        if (c1 == "X" && c2 == "X" && c3 == "X"){ return true; }
        if (c4 == "X" && c5 == "X" && c6 == "X"){ return true; }
        if (c7 == "X" && c8 == "X" && c9 == "X"){ return true; }
        if (c1 == "X" && c4 == "X" && c7 == "X"){ return true; }
        if (c2 == "X" && c5 == "X" && c8 == "X"){ return true; }
        if (c3 == "X" && c6 == "X" && c9 == "X"){ return true; }
        if (c1 == "X" && c5 == "X" && c9 == "X"){ return true; }
        if (c3 == "X" && c5 == "X" && c7 == "X"){ return true; }


        /* No win */
        return false;
    }

    private void winP1(){
        Toast.makeText(getApplicationContext(), R.string.p1win, Toast.LENGTH_LONG).show();
    }

    private void winP2(){
        Toast.makeText(getApplicationContext(), R.string.p2win, Toast.LENGTH_LONG).show();
    }

    private void tables(){
        Toast.makeText(getApplicationContext(), R.string.draw, Toast.LENGTH_LONG).show();
    }

    private void funcion() {
        if (player1Turn) {
            winP1();
            bloq();
        } else {
            winP2();
            bloq();
        }
    }

    private void funcion2() {
        if (turnos == 9){
            tables();
            bloq();
        }
    }

    private void bloq(){
        celda1.setEnabled(false);
        celda2.setEnabled(false);
        celda3.setEnabled(false);
        celda4.setEnabled(false);
        celda5.setEnabled(false);
        celda6.setEnabled(false);
        celda7.setEnabled(false);
        celda8.setEnabled(false);
        celda9.setEnabled(false);
    }

    private void color() {
        TextView P1 = findViewById(R.id.Pl1);
        TextView P2 = findViewById(R.id.Pl2);
        if (player1Turn){
            P2.setTextColor(Color.YELLOW);
            P1.setTextColor(Color.WHITE);
        } else {
            P1.setTextColor(Color.YELLOW);
            P2.setTextColor(Color.WHITE);
        }
    }
}
