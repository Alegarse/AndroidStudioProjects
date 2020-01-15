package com.ags.test_drawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ags.test_drawer.fragmentos.UnoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bnv = findViewById(R.id.bottomnavigation);

        NavController navController = Navigation.findNavController(this, R.id.fragmentTab);

        AppBarConfiguration abc = new AppBarConfiguration
                                    .Builder(R.id.uno, R.id.dos, R.id.tres)
                                    .build();

        NavigationUI.setupActionBarWithNavController(this,navController,abc);

        NavigationUI.setupWithNavController(bnv, navController);


       /* bnv.setOnNavigationItemSelectedListener(
                (menuItem) ->
                {
                    switch (menuItem.getItemId())
                    {
                        case R.id.uno:
                            Toast.makeText(this,"Pulsado uno", Toast.LENGTH_LONG).show();
                            break;
                        case R.id.dos:
                            Toast.makeText(this,"Pulsado dos", Toast.LENGTH_LONG).show();
                            break;
                        case R.id.tres:
                            Toast.makeText(this,"Pulsado tres", Toast.LENGTH_LONG).show();
                            break;

                    }
                    return true;
                }
        );*/
    }




}
