package com.ags.flixnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {

    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btnReg = findViewById(R.id.btnRegister);

        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Se haría todo el proceso de registro
                //......

                //Si se realiza con exito regresamos a la pagina principal
                setResult(RESULT_OK);
                finish();
                return;  //Sino ponemos esto da fallo
            }
        });

    }

}
