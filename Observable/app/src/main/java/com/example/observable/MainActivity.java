package com.example.observable;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private ObservableInteger a;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create observable int
        a = new ObservableInteger();
        // Set a default value to it
        a.setValue(0);

        // Create frag1
        Frag1 frag1 = new Frag1();
        Bundle args = new Bundle();
        // Put observable int (That why ObservableInteger implements Serializable)
        args.putSerializable(Frag1.PARAM, a);
        frag1.setArguments(args);

        // Add frag1 on screen
        getFragmentManager().beginTransaction().add(R.id.container, frag1).commit();

        // Add a button to change value of a dynamically
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set a new value in a
                a.setValue(a.getValue() + 1);
            }
        });
    }
}