package alegarse.sustituto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Variables activity
    private EditText email, password;
    private Button login;
    private TextView resetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instance for variables
        email = findViewById(R.id.userEmailLogin);
        password = findViewById(R.id.userPassLogin);
        login = findViewById(R.id.btnLogin);
        resetPass = findViewById(R.id.passLose);

        // Listener Login Button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
