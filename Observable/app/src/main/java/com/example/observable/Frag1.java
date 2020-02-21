package com.example.observable;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import java.util.Observable;
import java.util.Observer;

public class Frag1 extends Fragment {
    public static final String PARAM = "param";

    private ObservableInteger a1;
    private Observer a1Changed = new Observer() {
        @Override
        public void update(Observable o, Object newValue) {
            // a1 changed! (aka a changed)
            // newValue is the observable int value (it's the same as a1.getValue())
            Log.d(Frag1.class.getSimpleName(), "a1 has changed, new value:"+ (int) newValue);
        }
    };

    public Frag1() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Get ObservableInteger created in activity
            a1 = (ObservableInteger) getArguments().getSerializable(PARAM);
            // Add listener for value change
            a1.addObserver(a1Changed);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }
}