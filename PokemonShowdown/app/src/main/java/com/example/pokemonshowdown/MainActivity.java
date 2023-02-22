package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Pokemon> pokemonList ;

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pokemonList = new ArrayList<>();

        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));
        pokemonList.add(new Pokemon("Venusaur", R.drawable.p003));
        pokemonList.add(new Pokemon("Charizard", R.drawable.p006));
        pokemonList.add(new Pokemon("Blastoise", R.drawable.p009));




        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview_id);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, pokemonList);
        rv.setLayoutManager(new GridLayoutManager(this,3));
        rv.setAdapter(myAdapter);
        
    }
    
    
    
}