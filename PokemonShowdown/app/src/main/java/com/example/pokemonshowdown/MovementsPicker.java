package com.example.pokemonshowdown;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MovementsPicker extends AppCompatActivity {

    private List<Move> moves;

    private DBHandler handler;

    private List<Move> movesPk1py1;
    private List<Move> movesPk2py1;
    private List<Move> movesPk3py1;
    private List<Move> movesPk1py2;
    private List<Move> movesPk2py2;
    private List<Move> movesPk3py2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movements_picker);

        moves = new ArrayList<>();
        moves.add(new Move("Guillotina", 90000, 30));
        moves.add(new Move("Salpicadura", 0, 100));
        moves.add(new Move("Ataque Ala", 50, 100));
        moves.add(new Move("Rapidez", 50, 100));
        moves.add(new Move("Golpe alto", 80, 50));
        moves.add(new Move("Guillotina", 90000, 30));
        moves.add(new Move("Salpicadura", 0, 100));
        moves.add(new Move("Ataque Ala", 50, 100));
        moves.add(new Move("Rapidez", 50, 100));
        moves.add(new Move("Golpe alto", 80, 50));
        moves.add(new Move("Guillotina", 90000, 30));
        moves.add(new Move("Salpicadura", 0, 100));
        moves.add(new Move("Ataque Ala", 50, 100));
        moves.add(new Move("Rapidez", 50, 100));
        moves.add(new Move("Golpe alto", 80, 50));

        RecyclerView recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ListAdapter listAdapter = new ListAdapter(moves, this);
        recyclerView.setAdapter(listAdapter);

    }

    private void readMovesFromPokemons(){}
}