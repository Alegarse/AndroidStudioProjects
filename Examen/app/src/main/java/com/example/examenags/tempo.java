package com.example.examenags;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class tempo extends Fragment {

    private Button tempo;
    private Intent lanza;

    public tempo() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tempo, container, false);

        tempo = view.findViewById(R.id.lanzartempo);

        lanza = new Intent(getActivity(), tempoService.class);

        tempo.setOnClickListener(v -> {
            getActivity().startService(lanza);
        });
        return view;
    }

}
