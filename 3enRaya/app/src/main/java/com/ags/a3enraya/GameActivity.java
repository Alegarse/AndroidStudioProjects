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

        Intent intent = getIntent();
        String Player1 = intent.getStringExtra("nom1");
        String Player2 = intent.getStringExtra("nom2");

        final TextView P1 = (TextView) findViewById(R.id.Pl1);
        final TextView P2 = (TextView) findViewById(R.id.Pl2);

        P1.setText(Player1);
        P2.setText(Player2);

        if (player1Turn) {
            P1.setTextColor(Color.YELLOW);
        } else {
            P2.setTextColor(Color.YELLOW);
        }


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
                celda1.setEnabled(false);
                checkWin();
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
                celda2.setEnabled(false);
                checkWin();
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
                celda3.setEnabled(false);
                checkWin();
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
                celda4.setEnabled(false);
                checkWin();
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
                celda5.setEnabled(false);
                checkWin();
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
                celda6.setEnabled(false);
                checkWin();
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
                celda7.setEnabled(false);
                checkWin();
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
                celda8.setEnabled(false);
                checkWin();
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
                celda9.setEnabled(false);
                checkWin();
            }
        });


        if (checkWin()) {
            if (player1Turn) {
                winP1();
            } else {
                winP2();
            }
        } else if (turnos == 9) {
            tables();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private void reset() {

        celda1.setImageResource(R.drawable.v);
        celda1.setEnabled(true);
        celda2.setImageResource(R.drawable.v);
        celda2.setEnabled(true);
        celda3.setImageResource(R.drawable.v);
        celda3.setEnabled(true);
        celda4.setImageResource(R.drawable.v);
        celda4.setEnabled(true);
        celda5.setImageResource(R.drawable.v);
        celda5.setEnabled(true);
        celda6.setImageResource(R.drawable.v);
        celda6.setEnabled(true);
        celda7.setImageResource(R.drawable.v);
        celda7.setEnabled(true);
        celda8.setImageResource(R.drawable.v);
        celda8.setEnabled(true);
        celda9.setImageResource(R.drawable.v);
        celda9.setEnabled(true);

        player1Turn = true;
        turnos = 0;
    }

    private boolean checkWin() {

        /* Checking circle */

        if (celda1.getDrawable().equals(R.drawable.c) && celda2.getDrawable().equals(R.drawable.c) && celda3.getDrawable().equals(R.drawable.c) ) {
            return true;
        }
        if (celda4.getDrawable().equals(R.drawable.c) && celda5.getDrawable().equals(R.drawable.c) && celda6.getDrawable().equals(R.drawable.c) ) {
            return true;
        }
        if (celda7.getDrawable().equals(R.drawable.c) && celda8.getDrawable().equals(R.drawable.c) && celda9.getDrawable().equals(R.drawable.c) ) {
            return true;
        }
        if (celda1.getDrawable().equals(R.drawable.c) && celda4.getDrawable().equals(R.drawable.c) && celda7.getDrawable().equals(R.drawable.c) ) {
            return true;
        }
        if (celda2.getDrawable().equals(R.drawable.c) && celda5.getDrawable().equals(R.drawable.c) && celda8.getDrawable().equals(R.drawable.c) ) {
            return true;
        }
        if (celda3.getDrawable().equals(R.drawable.c) && celda6.getDrawable().equals(R.drawable.c) && celda9.getDrawable().equals(R.drawable.c) ) {
            return true;
        }
        if (celda1.getDrawable().equals(R.drawable.c) && celda5.getDrawable().equals(R.drawable.c) && celda9.getDrawable().equals(R.drawable.c) ) {
            return true;
        }
        if (celda3.getDrawable().equals(R.drawable.c) && celda5.getDrawable().equals(R.drawable.c) && celda7.getDrawable().equals(R.drawable.c) ) {
            return true;
        }

        /* Checking equis */

        if (celda1.getDrawable().equals(R.drawable.x) && celda2.getDrawable().equals(R.drawable.x) && celda3.getDrawable().equals(R.drawable.x) ) {
            return true;
        }
        if (celda4.getDrawable().equals(R.drawable.x) && celda5.getDrawable().equals(R.drawable.x) && celda6.getDrawable().equals(R.drawable.x) ) {
            return true;
        }
        if (celda7.getDrawable().equals(R.drawable.x) && celda8.getDrawable().equals(R.drawable.x) && celda9.getDrawable().equals(R.drawable.x) ) {
            return true;
        }
        if (celda1.getDrawable().equals(R.drawable.x) && celda4.getDrawable().equals(R.drawable.x) && celda7.getDrawable().equals(R.drawable.x) ) {
            return true;
        }
        if (celda2.getDrawable().equals(R.drawable.x) && celda5.getDrawable().equals(R.drawable.x) && celda8.getDrawable().equals(R.drawable.x) ) {
            return true;
        }
        if (celda3.getDrawable().equals(R.drawable.x) && celda6.getDrawable().equals(R.drawable.x) && celda9.getDrawable().equals(R.drawable.x) ) {
            return true;
        }
        if (celda1.getDrawable().equals(R.drawable.x) && celda5.getDrawable().equals(R.drawable.x) && celda9.getDrawable().equals(R.drawable.x) ) {
            return true;
        }
        if (celda3.getDrawable().equals(R.drawable.x) && celda5.getDrawable().equals(R.drawable.x) && celda7.getDrawable().equals(R.drawable.x) ) {
            return true;
        }

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


}
