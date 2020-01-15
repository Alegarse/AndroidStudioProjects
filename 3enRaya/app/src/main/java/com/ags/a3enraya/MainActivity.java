package com.ags.a3enraya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private Button enter;
    private EditText player1;
    private EditText player2;

    private String nomP1,nomP2;

   /* public static final String nom1 = "";
    public static final String nom2 = "";*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enter = findViewById(R.id.enter);
        player1 = findViewById(R.id.player1);
        player2 = findViewById(R.id.player2);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nomP1 = player1.getText().toString().trim();
                nomP2 = player2.getText().toString().trim();

                if (nomP1.isEmpty() || nomP2.isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.nameR, Toast.LENGTH_LONG).show();
                } else{
                    Intent initiate = new Intent (MainActivity.this, GameActivity.class);
                    initiate.putExtra("nom1", nomP1);
                    initiate.putExtra("nom2", nomP2);
                    startActivity(initiate);
                }

            }
        });
    }

}
