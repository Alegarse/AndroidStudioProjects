package com.example.observable;

import java.io.Serializable;
import java.util.Observable;

public class ObservableInteger extends Observable implements Serializable {

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        this.setChanged();
        this.notifyObservers(value);
    }
}