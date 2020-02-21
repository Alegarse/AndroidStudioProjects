package com.example.examenags;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bnv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottom_navigation);

        NavController nc = Navigation.findNavController(this,R.id.fragmentTab);

        AppBarConfiguration abc = new AppBarConfiguration
                                        .Builder(R.id.alarma, R.id.tempo)
                                        .build();

        NavigationUI.setupActionBarWithNavController(this,nc,abc);

        NavigationUI.setupWithNavController(bnv,nc);



    }
}
