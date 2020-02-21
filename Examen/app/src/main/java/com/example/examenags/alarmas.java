package com.example.examenags;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class alarmas extends Fragment {

    private Button alarma1, alarma2, alarma3;

    // Para las alarmas
    private AlarmManager am;
    private PendingIntent ai;


    public alarmas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarmas, container, false);

        alarma1 = view.findViewById(R.id.alarma1);
        alarma2 = view.findViewById(R.id.alarma2);
        alarma3 = view.findViewById(R.id.alarma3);

        alarma1.setOnClickListener(v -> {

            // Alarma a las 6:00 am
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 6);
        });

        alarma2.setOnClickListener(v -> {

            // Alarma a las 6:20 am
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 6);
            calendar.set(Calendar.MINUTE, 20);
        });

        alarma3.setOnClickListener(v -> {

            // Alarma a las 8:00 am
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 7);
        });

        return view;
    }

}
