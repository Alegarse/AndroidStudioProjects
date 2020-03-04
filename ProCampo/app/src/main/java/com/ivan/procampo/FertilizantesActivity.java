package com.ivan.procampo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ivan.procampo.adaptadores.FertilizanteAdapter;
import com.ivan.procampo.modelos.Fertilizantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FertilizantesActivity extends AppCompatActivity {


 DatabaseReference reference;
 RecyclerView recyclerView;

 ArrayList<Fertilizantes> list;

 FertilizanteAdapter adapter;

 SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizantes);

        recyclerView = findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.buscamelo);



        list = new ArrayList<Fertilizantes>();

        reference = FirebaseDatabase.getInstance().getReference().child("FERTILIZANTES");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                    Fertilizantes f = dataSnapshot1.getValue(Fertilizantes.class);

                    list.add(f);
                }

                adapter = new FertilizanteAdapter(FertilizantesActivity.this,list);

                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FertilizantesActivity.this,"No",Toast.LENGTH_SHORT).show();
            }
        });


        if(reference != null){
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });

        }

    }

    private void search(String str) {
        ArrayList<Fertilizantes> myList = new ArrayList<>();

        for(Fertilizantes object : list){
            if (object.getNombre().toLowerCase().contains(str.toLowerCase())){

                myList.add(object);

            }

        }

        FertilizanteAdapter adapterClass = new FertilizanteAdapter(FertilizantesActivity.this,myList);
        recyclerView.setAdapter(adapterClass);
    }


}
