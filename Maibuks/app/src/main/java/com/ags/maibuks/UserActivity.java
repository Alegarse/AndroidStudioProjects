package com.ags.maibuks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {

    // Variable paraFB
    private FirebaseAuth mAuth ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();


    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater() ;
        inflater.inflate(R.menu.menu_user, menu) ;
        //
        return true ;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.mnuLogout :

                // cerramos la sesión en FireBase
                mAuth.signOut() ;

                // volvemos a la actividad principal
                setResult(0) ;
                finish() ;
                return true ;
        }

        return super.onOptionsItemSelected(item);
    }
}
