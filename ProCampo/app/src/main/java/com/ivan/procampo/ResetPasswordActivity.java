package com.ivan.procampo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText mEditTextEmail;
    private Button mButtonResetPassword;

    private String email = "";

    private FirebaseAuth mAuth;

    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(this);

        mEditTextEmail = findViewById(R.id.correoReset);
        mButtonResetPassword = findViewById(R.id.btnResetPassword);

        mButtonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = mEditTextEmail.getText().toString();

                if(!email.isEmpty()){
                    mDialog.setMessage("Espere un momento mientras mandado su correo");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetPassword();

                } else{
                    Toast.makeText(ResetPasswordActivity.this,"Debe ingresar un correo",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void resetPassword() {
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this,"Correo enviado correctamente, revise su e-mail",Toast.LENGTH_SHORT).show();
                    Intent volvemosAlLogin = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                    startActivity(volvemosAlLogin);

                }else{
                    Toast.makeText(ResetPasswordActivity.this,"No hemos podido mandar el correo",Toast.LENGTH_SHORT).show();
                }

                mDialog.dismiss();
            }
        });

    }
}
